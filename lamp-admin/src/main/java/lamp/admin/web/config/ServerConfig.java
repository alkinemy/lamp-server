package lamp.admin.web.config;

import lamp.admin.web.service.AlarmEventService;
import lamp.admin.web.service.AlarmService;
import lamp.alarm.health.service.HealthWatcher;
import lamp.collector.core.config.CollectorCoreConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CollectorCoreConfig.class})
public class ServerConfig {


	@Bean
	public HealthWatcher healthWatcher(AlarmService alarmService, AlarmEventService alarmEventService) {
		return new HealthWatcher(alarmService, alarmEventService);
	}

}
