package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.*;

import lamp.common.utils.IOUtils;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;
import net.schmizz.sshj.userauth.method.AuthPublickey;
import net.schmizz.sshj.userauth.password.PasswordFinder;
import net.schmizz.sshj.userauth.password.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.concurrent.TimeUnit;

@Service
public class HostAgentInstallService {



	public void installAgent(ScannedHost scannedHost, HostCredentials hostCredentials, HostsConfiguration hostsConfiguration) throws Exception {
		try (final SSHClient client = new SSHClient()) {
			client.addHostKeyVerifier(new PromiscuousVerifier());
			client.connect(scannedHost.getAddress(), hostCredentials.getSshPort());

			if (hostCredentials.isUsePassword()) {
				client.authPassword(hostCredentials.getUsername(), hostCredentials.getPassword());
			} else {
				PKCS8KeyFile keyFile = new PKCS8KeyFile();
				keyFile.init(new StringReader(hostCredentials.getPrivateKey()));
				client.auth(hostCredentials.getUsername(), new AuthPublickey(keyFile));
			}

			try (final Session session = client.startSession()) {
				final Session.Command command = session.exec("ls -al");
				String response = IOUtils.toString(command.getInputStream());
				command.join(10, TimeUnit.SECONDS);
				System.out.println(response);
			}
		}

	}

}



