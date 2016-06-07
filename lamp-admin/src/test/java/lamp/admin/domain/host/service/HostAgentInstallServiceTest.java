package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.AgentInstallProperties;
import lamp.admin.domain.host.model.HostCredentials;
import lamp.admin.domain.host.model.HostConfiguration;
import lamp.admin.domain.host.service.form.HostAgentFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@RunWith(MockitoJUnitRunner.class)
public class HostAgentInstallServiceTest {

	@InjectMocks HostAgentInstallService hostAgentInstallService;
	@Spy AgentInstallProperties agentInstallProperties = new AgentInstallProperties();

	@Test
	public void testInstallAgent() throws Exception {
		String address = System.getProperty("scannedHost.address", "127.0.0.1");
		String username = System.getProperty("hostCredentials.username");
		String password = System.getProperty("hostCredentials.password");

		ResourceLoader resourceLoader = new DefaultResourceLoader();
		agentInstallProperties.setResourceLoader(resourceLoader);

		HostCredentials hostCredentials = new HostCredentials();
		hostCredentials.setUsePassword(true);
		hostCredentials.setUsername(username);
		hostCredentials.setPassword(password);

		String agentInstallFilename = "lamp-agent.jar";
		String agentFile = "classpath:agent/lamp-agent.jar";
		String agentInstallDirectory = "/lamp/agent/2";

		HostConfiguration hostConfiguration = new HostConfiguration();
		hostAgentInstallService.installAgent(address, hostCredentials,
											 agentFile,
											 agentInstallDirectory,
											 agentInstallFilename,
											 hostConfiguration,
											 System.out);
	}
}