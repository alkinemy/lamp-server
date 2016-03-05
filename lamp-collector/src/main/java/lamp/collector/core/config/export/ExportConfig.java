package lamp.collector.core.config.export;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.service.health.export.HealthExportKafkaService;
import lamp.collector.core.service.health.export.HealthExportService;
import lamp.collector.core.service.health.export.HealthExportSlf4jService;
import lamp.collector.core.service.metrics.export.MetricsExportKafkaService;
import lamp.collector.core.service.metrics.export.MetricsExportKairosdbService;
import lamp.collector.core.service.metrics.export.MetricsExportService;
import lamp.collector.core.service.metrics.export.MetricsExportSlf4jService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
		public HealthExportKafkaService appMetricsExportKafkaService(ExportHealthKafkaProperties properties) {
			return new HealthExportKafkaService(properties);
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

	@Bean
	@ConditionalOnMissingBean(HealthExportService.class)
	public HealthExportService healthExportService() {
		return new HealthExportSlf4jService("health");
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportMetricsKafkaProperties.class})
	public static class ExportMetricsKafkaConfig {
		@Bean
		public MetricsExportKafkaService appMetricsExportKafkaService(ExportMetricsKafkaProperties properties) {
			return new MetricsExportKafkaService(properties);
		}
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportHealthKairosdbProperties.class})
	public static class ExportMetricsKairosdbConfig {

		@Bean
		public MetricsExportKairosdbService AppMetricsExportKairosdbService(ExportMetricsKairosdbProperties properties) throws Exception {
			return new MetricsExportKairosdbService(properties);
		}
	}

	@Bean
	@ConditionalOnMissingBean(MetricsExportService.class)
	public MetricsExportService metricsExportService() {
		return new MetricsExportSlf4jService("metrics");
	}

}
