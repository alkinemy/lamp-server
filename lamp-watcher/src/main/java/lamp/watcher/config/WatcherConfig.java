package lamp.watcher.config;

import lamp.collector.core.config.CollectorCoreConfig;
import lamp.watcher.support.LampHttpRequestInterceptor;
import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Import(CollectorCoreConfig.class)
@EnableConfigurationProperties({ WatcherProperties.class, LampServerProperties.class })
public class WatcherConfig {

	@Bean
	public RestTemplate restTemplate(LampServerProperties serverProperties, WatcherProperties clientProperties) {

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(serverProperties.getConnectTimeout());
		clientHttpRequestFactory.setConnectionRequestTimeout(serverProperties.getConnectionRequestTimeout());
		clientHttpRequestFactory.setReadTimeout(serverProperties.getReadTimeout());

		RestTemplate template = new RestTemplate(clientHttpRequestFactory);
		template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new LampHttpRequestInterceptor(clientProperties));

		template.setInterceptors(interceptors);
		return template;
	}


}
