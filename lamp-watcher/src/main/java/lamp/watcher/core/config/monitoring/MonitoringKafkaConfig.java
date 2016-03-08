package lamp.watcher.core.config.monitoring;

import lamp.watcher.core.config.ConfigConstants;
import lamp.watcher.monitoring.collection.service.*;
import lamp.watcher.monitoring.kafka.service.MonitoringHealthKafkaService;
import lamp.watcher.monitoring.kafka.service.MonitoringMetricsKafkaService;
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
public class MonitoringKafkaConfig {

	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_HEALTH_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringHealthKafkaProperties.class})
	public static class MonitoringHealthKafkaConfig {

		@Bean
		public MonitoringHealthKafkaService monitoringHealthKafkaService(MonitoringHealthKafkaProperties properties) {
			return new MonitoringHealthKafkaService(properties);
		}

	}

	@Configuration
	@ConditionalOnProperty(name = ConfigConstants.MONITORING_METRICS_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ MonitoringMetricsKafkaProperties.class})
	public static class MonitoringMetricsKafkaConfig {

		@Bean
		public MonitoringMetricsKafkaService monitoringMetricsKafkaService(MonitoringMetricsKafkaProperties properties) {
			return new MonitoringMetricsKafkaService(properties);
		}

	}

}
