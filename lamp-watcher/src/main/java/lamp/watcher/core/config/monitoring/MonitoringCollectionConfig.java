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
	@EnableConfigurationProperties({ MonitoringHealthCollectionProperties.class})
	public static class MonitoringHealthCollectionConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringHealthCollectionProperties monitoringHealthCollectionProperties;

		@Bean
		public HealthLoaderService healthLoaderService() {
			return new HealthLoaderService();
		}

		@Bean
		public MonitoringHealthCollectionService monitoringHealthCollectionService() {
			return new MonitoringHealthCollectionService();
		}

		@Bean
		public MonitoringHealthCollectionScheduledService monitoringHealthScheduledService() {
			return new MonitoringHealthCollectionScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringHealthScheduledService().monitoring();
			}, monitoringHealthCollectionProperties.getPeriod());
			log.info("MonitoringHealthCollection Enabled");
		}
	}


	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_METRICS_COLLECTION_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsCollectionProperties.class})
	public static class MonitoringMetricsCollectionConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringMetricsCollectionProperties monitoringMetricsCollectionProperties;

		@Bean
		public MetricsLoaderService metricsLoaderService() {
			return new MetricsLoaderService();
		}

		@Bean
		public MonitoringMetricsCollectionService monitoringMetricsCollectionService() {
			return new MonitoringMetricsCollectionService();
		}

		@Bean
		public MonitoringMetricsCollectionScheduledService monitoringMetricsScheduledService() {
			return new MonitoringMetricsCollectionScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				monitoringMetricsScheduledService().monitoring();
			}, monitoringMetricsCollectionProperties.getPeriod());
			log.info("MonitoringMetricsCollection Enabled");
		}
	}

}
