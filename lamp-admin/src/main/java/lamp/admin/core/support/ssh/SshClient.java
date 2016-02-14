package lamp.admin.core.support.ssh;

import com.jcraft.jsch.*;
import lamp.admin.utils.FileUtils;
import lamp.admin.utils.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class SshClient {

	public static final int DEFAULT_PORT = 22;
	public static final int DEFAULT_FORWARDING_LOCAL_PORT = 2233;

	private String host;
	private int port = DEFAULT_PORT;

	@Getter
	private JSch jsch;
	@Getter
	private Session session;

	private boolean strictHostKeyChecking = false;

	@Getter
	private Session gatewaySession;
	private boolean useGateway = false;
	private String gatewayHost;
	private int gatewayPort = DEFAULT_PORT;
	private String localhost = "localhost";
	private int gatewayForwardingPort = DEFAULT_FORWARDING_LOCAL_PORT;
	private String gatewayUsername;
	private String gatewayPassword;

	public SshClient(String host) {
		this(host, DEFAULT_PORT);
	}

	public SshClient(String host, int port) {
		jsch = new JSch();
		this.host = host;
		this.port = port;
	}

	static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuilder sb = new StringBuilder();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			}
			while (c != '\n');
			if (b == 1) { // error
				log.warn("checkAck Error : {}", sb.toString());
			}
			if (b == 2) { // fatal error
				log.warn("checkAck Fatal Error : {}", sb.toString());
			}
		}
		return b;
	}



	public void connect(String username) {
		connect(username, null, null);
	}

	public void connect(String username, String privateKey, String password) {
		try {
			File knownHosts = new File("~/.ssh/known_hosts");
			if (knownHosts.exists()) {
				jsch.setKnownHosts("~/.ssh/known_hosts");
			}

			if (StringUtils.isBlank(privateKey)) {
				jsch.addIdentity("~/.ssh/id_rsa");
			} else {
				jsch.addIdentity(privateKey, password);
			}

			session = jsch.getSession(username, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
		} catch (JSchException e) {
			throw new SshException("ssh connection failed", e);
		}
	}

	public void connect(String username, String password) {
		try {
			if (useGateway) {
				Session gatewaySession = jsch.getSession(gatewayUsername, gatewayHost, gatewayPort);
				if (StringUtils.isNotBlank(gatewayPassword)) {
					gatewaySession.setUserInfo(new UserPasswordInfo(gatewayPassword));
				}
				gatewaySession.setConfig("StrictHostKeyChecking", "no");
				gatewaySession.setPortForwardingL(gatewayForwardingPort, host, port);
				gatewaySession.connect();
				gatewaySession.openChannel("direct-tcpip");

				session = jsch.getSession(username, localhost, gatewayForwardingPort);
				if (StringUtils.isNotBlank(gatewayPassword)) {
					session.setUserInfo(new UserPasswordInfo(gatewayPassword));
				}
				session.setConfig("StrictHostKeyChecking", "no");

				session.connect();
			} else {
				session = jsch.getSession(username, host, port);

				session.setConfig("StrictHostKeyChecking", "no");
				session.setPassword(password);
//				if (StringUtils.isNotBlank(password)) {
//
//					session.setUserInfo(new UserPasswordInfo(password));
//				}

				session.connect();
			}

		} catch (JSchException e) {
			throw new SshException("ssh connection failed", e);
		}
	}

	public void setGateway(String host, String username, String password) {
		setGateway(host, DEFAULT_PORT, DEFAULT_FORWARDING_LOCAL_PORT, username, password);
	}

	public void setGateway(String host, int port, String username, String password) {
		setGateway(host, port, DEFAULT_FORWARDING_LOCAL_PORT, username, password);
	}

	public void setGateway(String host, int port, int forwardingLocalport, String username, String password) {
		this.useGateway = true;

		this.gatewayHost = host;
		this.gatewayPort = port;
		this.gatewayForwardingPort = forwardingLocalport;
		this.gatewayUsername = username;
		this.gatewayPassword = password;
	}

	public void disconnect() {
		if (session != null) {
			session.disconnect();
		}
		if (gatewaySession != null) {
			gatewaySession.disconnect();
		}
	}

	public void mkdir(String path) {
		exec("mkdir -p " + path);
	}

	public boolean scpTo(File localFile, String remoteFilename) {
		boolean ptimestamp = true;
		String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + remoteFilename;
		Channel channel = null;
		try {
			// exec 'scp -t remotefile' remotely
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			if (checkAck(in) != 0) {
				return false;
			}

			if (ptimestamp) {
				command = "T" + (localFile.lastModified() / 1000) + " 0";
				// The access time should be sent here,
				// but it is not accessible with JavaAPI ;-<
				command += (" " + (localFile.lastModified() / 1000) + " 0\n");
				out.write(command.getBytes());
				out.flush();
				if (checkAck(in) != 0) {
					return false;
				}
			}

			// send "C0644 filesize filename", where filename should not include '/'
			long filesize = localFile.length();
			command = "C0644 " + filesize + " " + localFile.getName() + "\n";
			out.write(command.getBytes());
			out.flush();
			if (checkAck(in) != 0) {
				return false;
			}

			// send a content of lfile
			FileUtils.copyFile(localFile, out);

			// send '\0'
			out.write('\0');
			out.flush();

			if (checkAck(in) != 0) {
				return false;
			}
			out.close();

		} catch (Exception e) {
			throw new SshException("scp failed", e);
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
		}

		return true;
	}

	public void exec(String path, String command, PrintStream printStream, long timeout) {
		Channel channel = null;
		try {
			channel = getSession().openChannel("shell");
			try (Expect expect = new Expect(channel.getInputStream(), channel.getOutputStream(), printStream)) {
				channel.connect();

				expect.send("cd " + path + "\n");
				expect.send(command + "\n");
				expect.expectEOF(timeout);
			}
		} catch (Exception e) {
			throw new SshException("exec failed (path={}, command={}, timeout={})", e);
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	public void exec(String command) {
		try {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// X Forwarding
			// channel.setXForwarding(true);

			//channel.setInputStream(System.in);
			channel.setInputStream(null);

			//channel.setOutputStream(System.out);

			//FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			//((ChannelExec)channel).setErrStream(fos);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					log.debug("exec : {},", new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					log.debug("exit-status : {}", channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
		} catch (Exception e) {
			throw new SshException("ssh exec failed", e);
		}

	}



	public static class UserPasswordInfo implements UserInfo {

		private int promptPasswordCount;
		private String password;

		public UserPasswordInfo(String password) {
			this.password = password;
		}

		@Override public String getPassphrase() {
			log.debug("getPassphrase()");
			return null;
		}

		@Override public String getPassword() {
			log.debug("getPassword()");
			return password;
		}

		@Override public boolean promptPassword(String message) {
			log.debug("promptPassword({}) = {}", promptPasswordCount, message);
			return promptPasswordCount++ == 0;
		}

		@Override public boolean promptPassphrase(String message) {
			log.debug("promptPassphrase = {}", message);
			return true;
		}

		@Override public boolean promptYesNo(String message) {
			log.debug("promptYesNo = {}", message);
			return true;
		}

		@Override public void showMessage(String message) {
			log.debug("showMessage = {}l", message);
		}
	}
}
