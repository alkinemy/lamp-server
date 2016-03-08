package lamp.watcher.core.config;

import lamp.common.utils.assembler.SmartAssembler;
import lamp.watcher.core.service.MonitoringTargetService;
import lamp.watcher.support.jpa.service.JpaMonitoringTargetService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatcherConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

	@Bean
	@ConditionalOnMissingBean
	//	@ConditionalOnProperty(name = "spring.datasource.url")
	public MonitoringTargetService monitoringTargetService() {
		return new JpaMonitoringTargetService();
	}

}
