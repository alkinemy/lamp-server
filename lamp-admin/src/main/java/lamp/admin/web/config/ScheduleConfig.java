package lamp.admin.web.config;

import lamp.admin.web.service.AgentScheduledCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

	@Autowired
	private ServerProperties serverProperties;

	@Autowired
	private AgentScheduledCheckService agentScheduledCheckService;

	@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		Long healthCheckPeriod = serverProperties.getHealthCheckPeriod();
		if (healthCheckPeriod != null && healthCheckPeriod > 0) {
			taskRegistrar.addFixedRateTask(() -> {
				agentScheduledCheckService.checkHealth();
			}, healthCheckPeriod);
		}

		Long metricsCheckPeriod = serverProperties.getMetricsCheckPeriod();
		if (metricsCheckPeriod != null && metricsCheckPeriod > 0) {
			taskRegistrar.addFixedRateTask(() -> {
				agentScheduledCheckService.collectMetrics();
			}, metricsCheckPeriod);
		}
	}

}
