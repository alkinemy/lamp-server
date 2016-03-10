package lamp.collector.core.config.export;

import lamp.collector.core.CollectorConstants;
import lamp.common.metrics.HealthExporter;
import lamp.common.metrics.MetricsExporter;
import lamp.metrics.exporter.kafka.KafkaHealthExporter;
import lamp.metrics.exporter.kafka.KafkaMetricsExporter;
import lamp.metrics.exporter.kairosdb.KairosdbMetricsExporter;
import lamp.metrics.exporter.slf4j.Slf4jHealthExporter;
import lamp.metrics.exporter.slf4j.Slf4jMetricsExporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExporterConfig {

	@ConditionalOnProperty(name = CollectorConstants.EXPORT_HEALTH_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ KafkaHealthExporterProperties.class})
	public static class ExportHealthKafkaConfig {

		@Bean
		public KafkaHealthExporter kafkaHealthExporter(KafkaHealthExporterProperties properties) {
			return new KafkaHealthExporter(properties);
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
	@ConditionalOnMissingBean(HealthExporter.class)
	public HealthExporter slf4jHealthExporter() {
		return new Slf4jHealthExporter("health");
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ KafkaMetricsExporterProperties.class})
	public static class ExportMetricsKafkaConfig {
		@Bean
		public KafkaMetricsExporter kafkaMetricsExporter(KafkaMetricsExporterProperties properties) {
			return new KafkaMetricsExporter(properties);
		}
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ KairosdbHealthExporterProperties.class})
	public static class ExportMetricsKairosdbConfig {

		@Bean
		public KairosdbMetricsExporter kairosdbMetricsExporter(KairosdbMetricsExporterProperties properties) throws Exception {
			return new KairosdbMetricsExporter(properties);
		}
	}

	@Bean
	@ConditionalOnMissingBean(MetricsExporter.class)
	public MetricsExporter metricsExporter() {
		return new Slf4jMetricsExporter("metrics");
	}

}
