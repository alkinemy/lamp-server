package lamp.watcher.kairosdb.config;

import lamp.watcher.core.config.ConfigConstants;
import lamp.watcher.kairosdb.service.MonitoringHealthScheduledService;
import lamp.watcher.kairosdb.service.MonitoringMetricsScheduledService;
import lamp.watcher.monitoring.kairosdb.MonitoringHealthKairosdbScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Slf4j
@Configuration
public class MonitoringConfig {

	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_HEALTH_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringHealthProperties.class})
	public static class MonitoringHealthConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringHealthProperties monitoringHealthProperties;

		@Bean
		public MonitoringHealthScheduledService monitoringHealthScheduledService() {
			return new MonitoringHealthScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringHealthScheduledService().monitoring();
			}, monitoringHealthProperties.getInterval());
			log.info("Monitoring Health Enabled (interval={}ms)", monitoringHealthProperties.getInterval());
		}
	}


	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_METRICS_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsProperties.class})
	public static class MonitoringMetricsConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringMetricsProperties monitoringMetricsProperties;

		@Bean
		public MonitoringMetricsScheduledService monitoringMetricsScheduledService() {
			return new MonitoringMetricsScheduledService();
		}


		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringMetricsScheduledService().monitoring();
			}, monitoringMetricsProperties.getInterval());
			log.info("Monitoring Metrics Enabled (interval={}ms)", monitoringMetricsProperties.getInterval());
		}
	}

}
