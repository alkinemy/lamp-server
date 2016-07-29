package lamp.admin.domain.host.service;

import lamp.admin.core.host.HostCredentials;
import lamp.admin.domain.host.model.*;
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
		hostCredentials.setAuthType(HostAuthType.PASSWORD);
		hostCredentials.setUsername(username);
		hostCredentials.setPassword(password);

		HostConfiguration hostConfiguration = new HostConfiguration();

		String agentInstallDirectory = "/lamp/agent/2";
		String agentInstallFilename = "lamp-agent.jar";
		String agentFile = "classpath:agent/lamp-agent.jar";

		AgentInstallMetadata agentInstallMetadata = new AgentInstallMetadata();
		agentInstallMetadata.setAgentId("test");
		agentInstallMetadata.setAddress(address);
		agentInstallMetadata.setHostCredentials(hostCredentials);
		agentInstallMetadata.setHostConfiguration(hostConfiguration);
		agentInstallMetadata.setAgentInstallDirectory(agentInstallDirectory);
		agentInstallMetadata.setAgentInstallFilename(agentInstallFilename);
		agentInstallMetadata.setAgentFile(agentFile);

		AgentInstallResult agentInstallResult = new AgentInstallResult();
		hostAgentInstallService.install(agentInstallResult, agentInstallMetadata, System.out);
	}
}