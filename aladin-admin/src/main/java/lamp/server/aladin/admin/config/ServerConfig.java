package lamp.server.aladin.admin.config;


import lamp.server.aladin.admin.security.MetricsToKairosdbService;
import lamp.server.aladin.admin.support.MenuItemInterceptor;
import lamp.server.aladin.admin.support.error.LampErrorAttributes;
import lamp.server.aladin.core.service.MavenAppResourceLoader;
import lamp.server.aladin.core.support.agent.AgentClient;
import lamp.server.aladin.core.support.agent.AgentHttpRequestInterceptor;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableConfigurationProperties({ ServerProperties.class, AgentProperties.class, KairosdbProperties.class })
public class ServerConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private MenuItemInterceptor menuItemInterceptor;

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

	@Bean
	public AgentClient agentClient() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new AgentHttpRequestInterceptor());
		return new AgentClient(restTemplate);
	}

	@Bean
	@ConditionalOnProperty(name = "kairosdb.url")
	public MetricsToKairosdbService metricsToKairosdbService() {
		return new MetricsToKairosdbService();
	}

	@Bean
	public MavenAppResourceLoader mavenAppResourceLoader(ServerProperties serverProperties) {
		return new MavenAppResourceLoader(serverProperties.getMavenAppRepository());
	}

	@Bean
	public LampErrorAttributes errorAttributes() {
		return new LampErrorAttributes();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(menuItemInterceptor);
	}
}
