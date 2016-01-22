package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampServer;
import lamp.server.aladin.api.dto.AgentRegisterForm;
import lamp.server.aladin.core.dto.AgentDto;
import lamp.server.aladin.core.dto.AgentInstallForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LampServer.class)
@WebIntegrationTest("server.port:0")
public class TargetServerControllerTest {

	@Autowired
	EmbeddedWebApplicationContext server;

	@Value("${local.server.port}")
	int port;

	RestTemplate template = new TestRestTemplate();

	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testAgentInstall() throws Exception {

	}
}