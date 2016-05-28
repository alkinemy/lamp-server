package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.ScannedHost;
import lamp.common.utils.InetAddressUtils;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class HostScanService {

	public ScannedHost scanHost(String host, int port) {
		ScannedHost scannedHost = new ScannedHost();
		scannedHost.setName(InetAddressUtils.getCanonicalHostName(host, host));
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
			ssh.setTimeout(5 * 1000);
			ssh.setConnectTimeout(5 * 1000);
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



