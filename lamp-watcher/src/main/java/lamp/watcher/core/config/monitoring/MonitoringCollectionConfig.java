package lamp.watcher.core.config.monitoring;

import lamp.watcher.core.config.ConfigConstants;
import lamp.watcher.monitoring.collection.service.*;
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
public class MonitoringCollectionConfig {

	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_HEALTH_COLLECTION_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringHealthProperties.class})
	public static class MonitoringHealthConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringHealthProperties monitoringHealthProperties;

		@Bean
		public HealthLoaderService healthLoaderService() {
			return new HealthLoaderService();
		}

		@Bean
		public MonitoringHealthService monitoringHealthCollectionService() {
			return new MonitoringHealthService();
		}

		@Bean
		public MonitoringHealthScheduledService monitoringHealthScheduledService() {
			return new MonitoringHealthScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringHealthScheduledService().monitoring();
			}, monitoringHealthProperties.getPeriod());
			log.info("MonitoringHealthCollection Enabled");
		}
	}


	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_METRICS_COLLECTION_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsProperties.class})
	public static class MonitoringMetricsConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringMetricsProperties monitoringMetricsProperties;

		@Bean
		public MetricsLoaderService metricsLoaderService() {
			return new MetricsLoaderService();
		}

		@Bean
		public MonitoringMetricsService monitoringMetricsCollectionService() {
			return new MonitoringMetricsService();
		}

		@Bean
		public MonitoringMetricsScheduledService monitoringMetricsScheduledService() {
			return new MonitoringMetricsScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringMetricsScheduledService().monitoring();
			}, monitoringMetricsProperties.getPeriod());
			log.info("MonitoringMetricsCollection Enabled");
		}
	}

}
