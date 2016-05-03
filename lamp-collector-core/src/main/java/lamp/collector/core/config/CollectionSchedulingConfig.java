package lamp.collector.core.config;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.service.HealthProcessScheduledService;
import lamp.collector.core.service.MetricsProcessScheduledService;
import lamp.collector.core.service.metrics.MetricsKafkaCollectionService;
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
public class CollectionSchedulingConfig {

	@Configuration
	@ConditionalOnProperty(name = CollectorConstants.COLLECTION_HEALTH_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ CollectionHealthProperties.class})
	public static class CollectionHealthConfig implements SchedulingConfigurer {

		@Autowired
		private CollectionHealthProperties collectionHealthProperties;

		@Bean
		public HealthProcessScheduledService healthCollectionScheduledService() {
			return new HealthProcessScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				healthCollectionScheduledService().process();
			}, collectionHealthProperties.getInterval());
			log.info("Collection Health Enabled (interval={}ms)", collectionHealthProperties.getInterval());
		}
	}

	@Configuration
	@ConditionalOnProperty(name = CollectorConstants.COLLECTION_METRICS_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ CollectionMetricsProperties.class})
	public static class CollectionMetricsConfig implements SchedulingConfigurer {

		@Autowired
		private CollectionMetricsProperties collectionMetricsProperties;

		@Bean
		public MetricsProcessScheduledService metricsCollectionScheduledService() {
			return new MetricsProcessScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				metricsCollectionScheduledService().process();
			}, collectionMetricsProperties.getInterval());
			log.info("Collection Metrics Enabled (interval={}ms)", collectionMetricsProperties.getInterval());
		}

		@Bean
		@ConditionalOnProperty(name = CollectorConstants.COLLECTION_METRICS_PREFIX + ".kafka.enabled", havingValue = "true")
		public MetricsKafkaCollectionService metricsKafkaCollectionService() {
			return new MetricsKafkaCollectionService(collectionMetricsProperties.getKafka());
		}
	}


}
