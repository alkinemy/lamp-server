package lamp.server.aladin.core.support.ssh;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Expect implements Closeable {

	public static final int RETURN_TIMEOUT= -1;
	public static final int RETURN_EOF = -2;
	public static final int RETURN_EXCEPTION = -9;
	private static final long DEFAULT_TIMEOUT = 10 * 1000;

	private Selector selector;

	private Pipe.SourceChannel inputChannel;
	private InputStream input;
	private OutputStream output;

	private boolean pumping = true;
	@Getter
	@Setter
	private long timeout = DEFAULT_TIMEOUT;

	@Getter
	@Setter
	private boolean resetTimeoutOnReceive = false;

	private PrintStream printStream;

	public Expect(InputStream input, OutputStream output) throws IOException {
		this(input, output, null);
	}

	public Expect(InputStream input, OutputStream output, PrintStream printStream) throws IOException {
		this.selector = Selector.open();
		this.inputChannel = inputStreamToSelectableChannel(input);
		this.inputChannel.register(selector, SelectionKey.OP_READ);

		this.input = input;
		this.output = output;
		this.printStream = printStream;
	}

	@Override
	public void close() throws IOException {
		close(true, true);
	}

	public void close(boolean closeInput, boolean closeOutput) throws IOException {
		this.pumping = false;

		IOUtils.closeQuietly(selector);
		IOUtils.closeQuietly(inputChannel);
		if (closeInput) {
			IOUtils.closeQuietly(input);
		}
		if (closeOutput) {
			IOUtils.closeQuietly(output);
		}
	}

	private Pipe.SourceChannel inputStreamToSelectableChannel(
		final InputStream input) throws IOException {
		Pipe pipe = Pipe.open();
		pipe.source().configureBlocking(false);

		Thread pumper = new Thread(() -> {
			byte[] buffer = new byte[1024];
			try (OutputStream out = Channels.newOutputStream(pipe.sink())) {
				int read = -1;
				while ( pumping && (read = input.read(buffer)) != -1) {
					out.write(buffer, 0, read);
//					log.info("[Pumping] {}", new String(buffer, 0, read));
					if (printStream != null) {
						printStream.print(new String(buffer, 0, read));
					}

				}
				log.debug("EOF from InputStream");
			} catch (IOException e) {
				log.warn("IOException when pumping from InputStream, now pumping piping thread will end", e);
			}
		});
		pumper.setName("Pumper Thread");
		pumper.setDaemon(true);
		pumper.start();

		return pipe.source();
	}

	public void send(String str) throws IOException {
		this.send(str.getBytes());
	}

	public void send(byte[] bytes) throws IOException {
		log.info("sending: " + bytesToPrintableString(bytes));

		output.write(bytes);
		output.flush();
	}

	public int expect(Pattern... patterns) {
		return expect(timeout, patterns);
	}

	public int expect(String... patterns) {
		return expect(timeout, patterns);
	}

	public int expect(long timeout, Pattern... patterns) {
		return expect(timeout, Arrays.asList(patterns));
	}

	public int expect(long timeout, String... patterns) {
		List<Pattern> list = new ArrayList<>();
		for (String pattern : patterns) {
			list.add(Pattern.compile(Pattern.quote(pattern)));
		}
		return expect(timeout, list);
	}

	public int expect(long timeout, List<Pattern> patterns) {
		try {
			expectOrThrow(timeout, patterns);
			return 0;
		} catch(ExpectTimeoutException e) {
			return RETURN_TIMEOUT;
		} catch(ExpectEOFException e) {
			return RETURN_EOF;
		} catch (Exception e) {
			return RETURN_EXCEPTION;
		}
	}

	public int expectEOF() {
		return expectEOF(timeout);
	}

	public int expectEOF(long timeout) {
		return expect(timeout, Collections.emptyList());
	}

	public void expectOrThrow(Pattern... patterns) {
		expectOrThrow(timeout, patterns);
	}

	public void expectOrThrow(String... patterns) {
		expectOrThrow(timeout, patterns);
	}

	public void expectOrThrow(long timeout, Pattern... patterns) {
		expectOrThrow(timeout, Arrays.asList(patterns));
	}

	public void expectOrThrow(long timeout, String... patterns) {
		List<Pattern> list = new ArrayList<>();
		for (String pattern : patterns) {
			list.add(Pattern.compile(Pattern.quote(pattern)));
		}
		expectOrThrow(timeout, list);
	}

	public void expectOrThrow(long timeout, List<Pattern> patterns) {
		log.debug("Expecting " + patterns);

		try {
			long endTime = System.currentTimeMillis() + timeout;

			StringBuilder input = new StringBuilder();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				long waitTime = endTime - System.currentTimeMillis();
				if (waitTime < 0) {
					throw new ExpectTimeoutException(timeout);
				}
				selector.select(waitTime);
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();

				while (it.hasNext()) {
					SelectionKey key = it.next();
					if (key.isReadable()) {
						if (resetTimeoutOnReceive) {
							endTime = System.currentTimeMillis() + timeout;
						}

						buffer.clear();
						Pipe.SourceChannel channel = (Pipe.SourceChannel) key.channel();
						int read = channel.read(buffer);
						if (read == -1) {
							throw new ExpectEOFException();
						}
						buffer.flip();

						byte[] bytes = new byte[read];
						buffer.get(bytes, 0, read);
						input.append(new String(bytes));

					}
					it.remove();
				}

				for (Pattern pattern : patterns) {
					Matcher m = pattern.matcher(input);
					if (m.find()) {
						log.debug("found : {}", m.group());
						input.setLength(0);
						return;
					}
				}
			}
		} catch (ExpectException e) {
			throw e;
		} catch (Exception e) {
			throw new ExpectException("expect failed", e);
		}
	}

	public void expectEOFOrThrow() {
		expectEOFOrThrow(timeout);
	}

	public void expectEOFOrThrow(long timeout) {
		try {
			expectOrThrow(timeout, Collections.emptyList());
		} catch (ExpectEOFException e) {
			// igrnore
		}
	}

	/**
	 * Static method used for convert byte array to string, each byte is
	 * converted to an ASCII character, if the byte represents a control
	 * character, it is replaced by a printable caret notation <a
	 * href="http://en.wikipedia.org/wiki/ASCII">
	 * http://en.wikipedia.org/wiki/ASCII </a>, or an escape code if possible.
	 *
	 * @param bytes
	 *            bytes to be printed
	 * @return String representation of the byte array
	 */
	public static String bytesToPrintableString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes)
			sb.append(byteToPrintableString(b));
		return sb.toString();
	}

	public static String byteToPrintableString(byte b) {
		String s = new String(new byte[] { b });
		// control characters
		if (b >= 0 && b < 32) s = "^" + (char) (b + 64);
		else if (b == 127) s = "^?";
		// some escape characters
		if (b == 9) s = "\\t";
		if (b == 10) s = "\\n";
		if (b == 13) s = "\\r";
		return s;
	}

}
