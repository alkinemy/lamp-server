package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.HostCredentials;
import lamp.admin.domain.host.model.HostsConfiguration;
import lamp.admin.domain.host.model.ScannedHost;
import lamp.common.utils.IOUtils;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.concurrent.TimeUnit;

@Service
public class HostAgentInstallService {


	public void installAgent(ScannedHost scannedHost, HostCredentials hostCredentials, HostsConfiguration hostsConfiguration) {
		try (final SSHClient client = new SSHClient()) {
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect(scannedHost.getAddress(), hostCredentials.getSshPort());

			if (hostCredentials.isUsePassword()) {
				client.authPassword(hostCredentials.getUsername(), hostCredentials.getPassword());
			} else {
				PKCS8KeyFile keyFile = new PKCS8KeyFile();
				keyFile.init(new StringReader(hostCredentials.getPrivateKey()));
				client.authPublickey(hostCredentials.getUsername(), keyFile);
			}

//			uploadAgent(client);
			client.newSCPFileTransfer().upload(new FileSystemFile(""), "/tmp/");



			try (final Session session = client.startSession()) {
				final Session.Command command = session.exec("ls -al");
				String response = IOUtils.toString(command.getInputStream());
				command.join(10, TimeUnit.SECONDS);
				System.out.println(response);
			}

		} catch (Exception e) {

		}
	}

	public void xxx() {
		// mkdir -p

	}

}



