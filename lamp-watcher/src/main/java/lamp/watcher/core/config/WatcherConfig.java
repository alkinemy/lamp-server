package lamp.watcher.core.config;

import lamp.admin.utils.assembler.SmartAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatcherConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

}
