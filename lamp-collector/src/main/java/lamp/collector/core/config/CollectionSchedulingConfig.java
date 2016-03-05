package lamp.collector.core.config;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.service.HealthCollectionScheduledService;
import lamp.collector.core.service.MetricsCollectionScheduledService;
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
		public HealthCollectionScheduledService appHealthCollectionScheduledService() {
			return new HealthCollectionScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedRateTask(() -> {
				appHealthCollectionScheduledService().collection();
			}, collectionHealthProperties.getPeriod());
			log.info("Collection Health Enabled ({} ms)", collectionHealthProperties.getPeriod());
		}
	}

	@Configuration
	@ConditionalOnProperty(name = CollectorConstants.COLLECTION_METRICS_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ CollectionMetricsProperties.class})
	public static class CollectionMetricsConfig implements SchedulingConfigurer {

		@Autowired
		private CollectionMetricsProperties collectionMetricsProperties;

		@Bean
		public MetricsCollectionScheduledService appMetricsCollectionScheduledService() {
			return new MetricsCollectionScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedRateTask(() -> {
				appMetricsCollectionScheduledService().collection();
			}, collectionMetricsProperties.getPeriod());
			log.info("Collection Metrics Enabled ({} ms)", collectionMetricsProperties.getPeriod());
		}
	}


}
