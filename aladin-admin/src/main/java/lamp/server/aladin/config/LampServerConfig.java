package lamp.server.aladin.config;

import lamp.server.aladin.common.assembler.SmartAssembler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ LampServerProperties.class })
public class LampServerConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

}
