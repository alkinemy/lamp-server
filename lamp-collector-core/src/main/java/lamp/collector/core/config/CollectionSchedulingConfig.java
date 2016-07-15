package lamp.collector.core.config;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.handler.TargetMetricsHandler;
import lamp.collector.core.metrics.processor.KafkaTargetMetricsProcessor;
import lamp.collector.core.metrics.processor.MetricsTargetCollectionProcessor;
import lamp.collector.core.metrics.processor.TargetMetricsProcessor;
import lamp.common.event.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;

@Slf4j
@Configuration
public class CollectionSchedulingConfig {

//	@Configuration
//	@ConditionalOnProperty(name = CollectorConstants.COLLECTION_HEALTH_PREFIX + ".enabled", havingValue = "true")
//	@EnableConfigurationProperties({ CollectionHealthProperties.class})
//	public static class CollectionHealthConfig implements SchedulingConfigurer {
//
//		@Autowired
//		private CollectionHealthProperties collectionHealthProperties;
//
//		@Bean
//		public HealthProcessScheduledService healthCollectionScheduledService() {
//			return new HealthProcessScheduledService();
//		}
//
//		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//			taskRegistrar.addFixedDelayTask(() -> {
//				healthCollectionScheduledService().process();
//			}, collectionHealthProperties.getInterval());
//			log.info("Collection Health Enabled (interval={}ms)", collectionHealthProperties.getInterval());
//		}
//	}

	@Configuration
	@ConditionalOnProperty(name = CollectorConstants.COLLECTION_METRICS_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ CollectionMetricsProperties.class})
	public static class CollectionMetricsConfig implements SchedulingConfigurer {

		@Autowired
		private CollectionMetricsProperties collectionMetricsProperties;

		@Bean
		public TargetMetricsProcessor targetMetricsProcessor(List<TargetMetricsHandler<TargetMetrics>> targetMetricsHandlers, EventPublisher eventPublisher) {
			return new TargetMetricsProcessor(targetMetricsHandlers, eventPublisher);
		}

		@Bean
		@ConditionalOnProperty(name = CollectorConstants.COLLECTION_METRICS_PREFIX + ".kafka.enabled", havingValue = "true")
		public KafkaTargetMetricsProcessor kafkaTargetMetricsProcessor(TargetMetricsProcessor targetMetricsProcessor) {
			return new KafkaTargetMetricsProcessor(collectionMetricsProperties.getKafka(), targetMetricsProcessor);
		}

		@Bean
		public MetricsTargetCollectionProcessor metricsTargetCollectionProcessor() {
			return new MetricsTargetCollectionProcessor();
		}


		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				metricsTargetCollectionProcessor().process();
			}, collectionMetricsProperties.getInterval());
			log.info("Collection Metrics Enabled (interval={}ms)", collectionMetricsProperties.getInterval());
		}

	}


}
