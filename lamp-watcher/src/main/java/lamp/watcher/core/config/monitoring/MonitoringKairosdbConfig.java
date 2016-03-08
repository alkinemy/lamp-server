package lamp.watcher.core.config.monitoring;

import lamp.watcher.core.config.ConfigConstants;
import lamp.watcher.monitoring.collection.service.HealthLoaderService;
import lamp.watcher.monitoring.collection.service.MetricsLoaderService;
import lamp.watcher.monitoring.kairosdb.MonitoringHealthKairosdbScheduledService;
import lamp.watcher.monitoring.kairosdb.MonitoringHealthKairosdbService;
import lamp.watcher.monitoring.kairosdb.MonitoringMetricsKairosdbScheduledService;
import lamp.watcher.monitoring.kairosdb.MonitoringMetricsKairosdbService;
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
public class MonitoringKairosdbConfig {

	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_HEALTH_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringHealthKairosdbProperties.class})
	public static class MonitoringHealthKairosdbConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringHealthKairosdbProperties monitoringHealthKairosdbProperties;

		@Bean
		public MonitoringHealthKairosdbScheduledService monitoringHealthScheduledService() {
			return new MonitoringHealthKairosdbScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringHealthScheduledService().monitoring();
			}, monitoringHealthKairosdbProperties.getPeriod());
			log.info("MonitoringHealthKairosdb Enabled");
		}
	}


	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsKairosdbProperties.class})
	public static class MonitoringMetricsKairosdbConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringMetricsKairosdbProperties monitoringMetricsKairosdbProperties;

		@Bean
		public MonitoringMetricsKairosdbService monitoringMetricsKairosdbService() {
			return new MonitoringMetricsKairosdbService();
		}

		@Bean
		public MonitoringMetricsKairosdbScheduledService monitoringMetricsScheduledService() {
			return new MonitoringMetricsKairosdbScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringMetricsScheduledService().monitoring();
			}, monitoringMetricsKairosdbProperties.getPeriod());
			log.info("MonitoringMetricsKairosdb Enabled");
		}
	}

}
