package lamp.admin.agent.controller;

import lamp.admin.LampAdmin;
import lamp.admin.core.agent.domain.AgentDto;
import lamp.admin.core.agent.domain.AgentRegisterForm;
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

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LampAdmin.class)
@WebIntegrationTest("server.port:0")
public class AgentApiControllerIntegrationTests {

	@Autowired
	EmbeddedWebApplicationContext server;

	@Value("${local.server.port}")
	int port;

	RestTemplate template = new TestRestTemplate();

	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void testRegister() throws Exception {
		AgentRegisterForm agentForm = new AgentRegisterForm();
		agentForm.setId("test-1");
		agentForm.setName("test");
		agentForm.setType("type");
		agentForm.setVersion("0.0.1");
		agentForm.setHostname("host");
		agentForm.setAddress("localhost");
		agentForm.setPort(1);
		agentForm.setProtocol("http");
		agentForm.setAppDirectory("/lamp/agent");

		AgentDto saved = template.postForObject(getBaseUrl() + "/api/agent", agentForm, AgentDto.class);

		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isEqualTo("test-1");
		assertThat(saved.getName()).isEqualTo("test");
		assertThat(saved.getType()).isEqualTo("type");
		assertThat(saved.getVersion()).isEqualTo("0.0.1");
		assertThat(saved.getHostname()).isEqualTo("host");
		assertThat(saved.getAddress()).isEqualTo("localhost");
		assertThat(saved.getPort()).isEqualTo(1);
		assertThat(saved.getProtocol()).isEqualTo("http");
	}

}