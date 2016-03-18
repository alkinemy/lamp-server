package lamp.admin.web.config;

import lamp.admin.web.service.AlertEventService;
import lamp.admin.web.service.AlarmService;
import lamp.alert.health.service.HealthMonitoringProcessor;
import lamp.collector.core.config.CollectorCoreConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CollectorCoreConfig.class})
public class ServerConfig {


	@Bean
	public HealthMonitoringProcessor healthWatcher(AlarmService alarmService, AlertEventService alarmEventService) {
		return new HealthMonitoringProcessor(alarmService, alarmEventService);
	}

}
