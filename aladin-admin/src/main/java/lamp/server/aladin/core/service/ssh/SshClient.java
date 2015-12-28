package lamp.server.aladin.core.service.ssh;

import com.jcraft.jsch.*;
import lamp.server.aladin.utils.FileUtils;
import lamp.server.aladin.utils.StringUtils;
import lamp.server.aladin.core.exception.SshException;

import java.io.*;

public class SshClient {

	public static final int DEFAULT_PORT = 22;

	private String host;
	private int port = DEFAULT_PORT;

	private JSch jsch;
	private Session session;

	public SshClient(String host) {
		this(host, DEFAULT_PORT);
	}

	public SshClient(String host, int port) {
		jsch = new JSch();
		this.host = host;
		this.port = port;
	}

	public void connect(String username, String password) {
		try {
			session = jsch.getSession(username, host, port);
			if (StringUtils.isNotBlank(password)) {
				session.setUserInfo(new UserPasswordInfo(password));
			}
			session.connect();
		} catch (JSchException e) {
			throw new SshException("ssh connection failed", e);
		}
	}

	public void disconnect() {
		if (session != null) {
			session.disconnect();
		}
	}

	public void mkdir(String path) {
		exec("mkdir -p " + path);
	}

	public boolean scpTo(File localFile, String remoteFilename) {
		boolean ptimestamp = true;

		try {
			// exec 'scp -t remotefile' remotely
			String command = "scp " + (ptimestamp ? "-p" :"") +" -t "+ remoteFilename;
			Channel channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			if(checkAck(in) != 0){
				return false;
			}

			if(ptimestamp){
				command = "T "+ (localFile.lastModified()/1000) + " 0";
				// The access time should be sent here,
				// but it is not accessible with JavaAPI ;-<
				command += (" "+ (localFile.lastModified()/1000) + " 0\n");
				out.write(command.getBytes());
				out.flush();
				if (checkAck(in) != 0) {
					return false;
				}
			}

			// send "C0644 filesize filename", where filename should not include '/'
			long filesize = localFile.length();
			command = "C0644 " + filesize + " " + localFile.getName() + "\n";
			out.write(command.getBytes()); out.flush();
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

			channel.disconnect();
		} catch (Exception e) {
			throw new SshException("scp copy failed", e);
		}

		return true;
	}

	public void exec(String path, String command) {

	}

	public void exec(String command) {
		try {
			Channel channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// X Forwarding
			// channel.setXForwarding(true);

			//channel.setInputStream(System.in);
			channel.setInputStream(null);

			//channel.setOutputStream(System.out);

			//FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			//((ChannelExec)channel).setErrStream(fos);
			((ChannelExec)channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp=new byte[1024];
			while(true){
				while(in.available()>0){
					int i=in.read(tmp, 0, 1024);
					if(i<0)break;
					System.out.print(new String(tmp, 0, i));
				}
				if(channel.isClosed()){
					if(in.available()>0) continue;
					System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try{Thread.sleep(1000);}catch(Exception ee){}
			}
			channel.disconnect();
		} catch (Exception e) {
			throw new SshException("ssh exec failed", e);
		}

	}

	static int checkAck(InputStream in) throws IOException {
		int b=in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if(b==0) return b;
		if(b==-1) return b;

		if(b==1 || b==2){
			StringBuffer sb=new StringBuffer();
			int c;
			do {
				c=in.read();
				sb.append((char)c);
			}
			while(c!='\n');
			if(b==1){ // error
				System.out.print(sb.toString());
			}
			if(b==2){ // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}


	public static class UserPasswordInfo implements UserInfo {

		private int promptPasswordCount;
		private String password;

		public UserPasswordInfo(String password) {
			this.password = password;
		}

		@Override public String getPassphrase() {
			System.out.println("getPassphrase()");
			return null;
		}

		@Override public String getPassword() {
			System.out.println("getPassword()");
			return password;
		}

		@Override public boolean promptPassword(String message) {
			System.out.println("promptPassword : " + promptPasswordCount + "\n" + message);
			return promptPasswordCount++ == 0;
		}

		@Override public boolean promptPassphrase(String message) {
			System.out.println("promptPassphrase : " + message);
			return true;
		}


		@Override public boolean promptYesNo(String message) {
			System.out.println("promptYesNo : " + message);
			return true;
		}

		@Override public void showMessage(String message) {
			System.out.println("showMessage : " + message);
		}
	}
}
