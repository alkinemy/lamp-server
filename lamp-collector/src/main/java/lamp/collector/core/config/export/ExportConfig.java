package lamp.collector.core.config.export;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.service.health.export.AppHealthExportKafkaService;
import lamp.collector.core.service.metrics.export.AppMetricsExportKafkaService;
import lamp.collector.core.service.metrics.export.AppMetricsExportKairosdbService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportConfig {

	@ConditionalOnProperty(name = CollectorConstants.EXPORT_HEALTH_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportHealthKafkaProperties.class})
	public static class ExportHealthKafkaConfig {
		@Bean
		public AppHealthExportKafkaService appMetricsExportKafkaService(ExportHealthKafkaProperties properties) {
			return new AppHealthExportKafkaService(properties);
		}
	}

//	@ConditionalOnProperty(name = CollectorConstants.EXPORT_HEALTH_KAIROSDB_PREFIX+ ".enabled", havingValue = "true")
//	@EnableConfigurationProperties({ ExportHealthKairosdbProperties.class})
//	public static class ExportHealthKairosdbConfig {
//		@Bean
//		public AppHealthExportKairosdbService appHealthExportKairosdbService(ExportHealthKairosdbProperties properties) {
//			return new AppHealthExportKairosdbService(properties);
//		}
//	}

	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportMetricsKafkaProperties.class})
	public static class ExportMetricsKafkaConfig {
		@Bean
		public AppMetricsExportKafkaService appMetricsExportKafkaService(ExportMetricsKafkaProperties properties) {
			return new AppMetricsExportKafkaService(properties);
		}
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportHealthKairosdbProperties.class})
	public static class ExportMetricsKairosdbConfig {

		@Bean
		public AppMetricsExportKairosdbService AppMetricsExportKairosdbService(ExportMetricsKairosdbProperties properties) throws Exception {
			return new AppMetricsExportKairosdbService(properties);
		}
	}

}
