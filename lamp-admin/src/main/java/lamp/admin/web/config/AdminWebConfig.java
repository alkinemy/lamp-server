package lamp.admin.web.config;


import lamp.admin.core.app.service.MavenAppResourceLoader;
import lamp.admin.core.support.agent.AgentClient;
import lamp.admin.core.support.agent.AgentHttpRequestInterceptor;
import lamp.admin.core.support.agent.AgentResponseErrorHandler;
import lamp.admin.utils.assembler.SmartAssembler;
import lamp.admin.web.support.LampMessageInterpolator;
import lamp.admin.web.support.MenuItemInterceptor;
import lamp.admin.web.support.error.LampErrorAttributes;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@Configuration
@EnableConfigurationProperties({ ServerProperties.class, AgentProperties.class})
public class AdminWebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private MenuItemInterceptor menuItemInterceptor;

	@Autowired
	private MessageSource messageSource;

	@Bean
	public IDialect java8TimeDialect () {
		return  new Java8TimeDialect();
	}

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

		ResourceBundleMessageInterpolator resourceBundleMessageInterpolator = new ResourceBundleMessageInterpolator(
				new MessageSourceResourceBundleLocator(messageSource));
		LampMessageInterpolator messageInterpolator = new LampMessageInterpolator(resourceBundleMessageInterpolator);
		bean.setMessageInterpolator(messageInterpolator);
		return bean;
	}

	@Bean
	public AgentClient agentClient(ServerProperties serverProperties) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(serverProperties.getConnectTimeout());
		clientHttpRequestFactory.setConnectionRequestTimeout(serverProperties.getConnectionRequestTimeout());
		clientHttpRequestFactory.setReadTimeout(serverProperties.getReadTimeout());

		AgentResponseErrorHandler errorHandler = new AgentResponseErrorHandler();
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.setErrorHandler(errorHandler);
		restTemplate.getInterceptors().add(new AgentHttpRequestInterceptor());
		return new AgentClient(restTemplate);
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
