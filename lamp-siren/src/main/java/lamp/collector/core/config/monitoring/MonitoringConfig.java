package lamp.collector.core.config.monitoring;

import lamp.collector.core.service.*;
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
@EnableConfigurationProperties({ MonitoringHealthAppProperties.class, MonitoringMetricsAppProperties.class })
public class MonitoringConfig {

	@Configuration
	@ConditionalOnProperty(name = "monitoring.health.app.enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringHealthAppProperties.class})
	public static class MonitoringHealthAppConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringHealthAppProperties monitoringHealthAppProperties;

		@Bean
		public AppHealthMonitoringAppService appHealthMonitoringAppService() {
			return new AppHealthMonitoringAppService();
		}

		@Bean
		public AppHealthMonitoringAppScheduledService appHealthMonitoringAppScheduledService() {
			return new AppHealthMonitoringAppScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedRateTask(() -> {
				appHealthMonitoringAppScheduledService().monitoring();
			}, monitoringHealthAppProperties.getPeriod());
			log.info("MonitoringHealthApp Enabled");
		}
	}


	@Configuration
	@ConditionalOnProperty(name = "monitoring.metrics.app.enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsAppProperties.class})
	public static class MonitoringMetricsAppConfig implements SchedulingConfigurer {

		@Autowired
		private MonitoringMetricsAppProperties monitoringMetricsAppProperties;

		@Bean
		public AppMetricsMonitoringAppService appMetricsMonitoringAppService() {
			return new AppMetricsMonitoringAppService();
		}

		@Bean
		public AppMetricsMonitoringAppScheduledService appMetricsMonitoringAppScheduledService() {
			return new AppMetricsMonitoringAppScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedRateTask(() -> {
				appMetricsMonitoringAppScheduledService().monitoring();
			}, monitoringMetricsAppProperties.getPeriod());
			log.info("MonitoringMetricsApp Enabled");
		}

	}

	@Configuration
	@ConditionalOnProperty(name = "monitoring.metrics.kafka.enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringHealthKafkaProperties.class})
	public static class MonitoringHealthKafkaConfig {
		@Bean
		public AppHealthMonitoringKafkaService appHealthMonitoringKafkaService(MonitoringHealthKafkaProperties properties) {
			return new AppHealthMonitoringKafkaService(properties);
		}

	}

	@Configuration
	@ConditionalOnProperty(name = "monitoring.metrics.kafka.enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsKafkaProperties.class})
	public static class MonitoringMetricsKafkaConfig {
		@Bean
		public AppMetricsMonitoringKafkaService appMetricsMonitoringKafkaService(MonitoringMetricsKafkaProperties properties) {
			return new AppMetricsMonitoringKafkaService(properties);
		}
	}

}
