package lamp.admin.core.host.scan;

import lamp.admin.domain.host.model.ScannedHost;
import lamp.common.utils.InetAddressUtils;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;

@Slf4j
public class HostScanner {

	private int sshTimeout = 2 * 1000;
	private int sshConnectionTimeout = 2 * 1000;

	public ScannedHost scanHost(String host, int port) {
		ScannedHost scannedHost = new ScannedHost();
		scannedHost.setQuery(host);
		scannedHost.setName(InetAddressUtils.getHostName(host, host));
		scannedHost.setAddress(InetAddressUtils.getHostAddress(host, host));

		long startTimeMillis = System.currentTimeMillis();
		boolean connected = scanHostConnected(host, port);
		scannedHost.setConnected(connected);
		if (connected) {
			scannedHost.setResponseTime(System.currentTimeMillis() - startTimeMillis);
		}

		boolean managed = scanHostManaged(host);
		scannedHost.setManaged(managed);
		return scannedHost;
	}

	protected boolean scanHostConnected(String host, int port) {
		boolean connected = false;

		try (SSHClient ssh = new SSHClient()) {
			ssh.setTimeout(sshTimeout);
			ssh.setConnectTimeout(sshConnectionTimeout);
			ssh.addHostKeyVerifier(new PromiscuousVerifier());
			ssh.connect(host, port);
			connected = ssh.isConnected();
		} catch (IOException e) {
			log.debug("scanHostConnected ", e);
		}

		return connected;
	}

	protected boolean scanHostManaged(String host) {
		return false;
	}

}



