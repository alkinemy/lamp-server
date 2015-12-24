package lamp.server.aladin.admin.config;


import lamp.server.aladin.common.assembler.SmartAssembler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ ServerProperties.class })
public class ServerConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

}
