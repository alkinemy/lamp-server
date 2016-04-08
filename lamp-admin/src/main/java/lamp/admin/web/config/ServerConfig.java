package lamp.admin.web.config;

import lamp.admin.web.service.AlarmService;
import lamp.admin.web.service.AlertEventService;
import lamp.collector.core.config.CollectorCoreConfig;
import lamp.monitoring.core.health.service.HealthMonitoringProcessor;
import lamp.monitoring.core.health.service.SimpleHealthMonitoringProcessor;
import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CollectorCoreConfig.class})
public class ServerConfig {

	@Bean
	public HealthMonitoringProcessor healthWatcher(AlarmService alarmService, AlertEventService alarmEventService) {
		return new SimpleHealthMonitoringProcessor(alarmService, alarmEventService);
	}

}
