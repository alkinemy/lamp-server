package lamp.admin.domain.host.service;

import lamp.admin.core.host.HostCredentials;
import lamp.admin.domain.host.model.AgentInstall;
import lamp.admin.domain.host.model.AgentInstallProperties;
import lamp.admin.domain.host.model.HostConfiguration;
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

		HostConfiguration hostConfiguration = new HostConfiguration();

		String agentInstallDirectory = "/lamp/agent/2";
		String agentInstallFilename = "lamp-agent.jar";
		String agentFile = "classpath:agent/lamp-agent.jar";

		AgentInstall agentInstall = new AgentInstall();
		agentInstall.setAgentId("test");
		agentInstall.setAddress(address);
		agentInstall.setHostCredentials(hostCredentials);
		agentInstall.setHostConfiguration(hostConfiguration);
		agentInstall.setAgentInstallDirectory(agentInstallDirectory);
		agentInstall.setAgentInstallFilename(agentInstallFilename);
		agentInstall.setAgentFile(agentFile);


		hostAgentInstallService.installAgent(agentInstall, System.out);
	}
}