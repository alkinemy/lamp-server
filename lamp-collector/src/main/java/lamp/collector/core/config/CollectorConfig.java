package lamp.collector.core.config;

import lamp.admin.utils.assembler.SmartAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectorConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}


}
