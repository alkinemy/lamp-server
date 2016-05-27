package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.HostCredentials;
import lamp.admin.domain.host.model.HostsConfiguration;
import lamp.admin.domain.host.model.ScannedHost;
import org.junit.Test;

import static org.junit.Assert.*;


public class HostAgentInstallServiceTest {

	@Test
	public void testInstallAgent() throws Exception {
		HostAgentInstallService hostAgentInstallService = new HostAgentInstallService();
		ScannedHost scannedHost = new ScannedHost();
		scannedHost.setAddress("127.0.0.1");

		HostCredentials hostCredentials = new HostCredentials();
		hostCredentials.setUsePassword(false);
		hostCredentials.setUsername("ubuntu");
		hostCredentials.setPrivateKey("");

		HostsConfiguration hostsConfiguration = new HostsConfiguration();
		hostAgentInstallService.installAgent(scannedHost, hostCredentials, hostsConfiguration);
	}
}