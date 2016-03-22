package lamp.collector.core.config.export;

import lamp.collector.core.CollectorConstants;
import lamp.collector.health.exporter.HealthExporter;
import lamp.collector.health.exporter.kafka.KafkaHealthExporter;
import lamp.collector.health.exporter.slf4j.Slf4jHealthExporter;
import lamp.collector.metrics.exporter.MetricsExporter;
import lamp.collector.metrics.exporter.kafka.KafkaMetricsExporter;
import lamp.collector.metrics.exporter.kairosdb.KairosdbMetricsExporter;
import lamp.collector.metrics.exporter.slf4j.Slf4jMetricsExporter;
import lamp.common.event.EventPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExporterConfig {

		@ConditionalOnProperty(name = CollectorConstants.EXPORT_HEALTH_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ KafkaHealthExporterProperties.class})
	public static class hKafkaHealtExportConfig {

		@Bean
		public KafkaHealthExporter kafkaHealthExporter(EventPublisher eventPublisher, KafkaHealthExporterProperties properties) {
			return new KafkaHealthExporter(eventPublisher, properties);
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
	public static class KafkaMetricsExportConfig {
		@Bean
		public KafkaMetricsExporter kafkaMetricsExporter(EventPublisher eventPublisher, KafkaMetricsExporterProperties properties) {
			return new KafkaMetricsExporter(eventPublisher, properties);
		}
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ KairosdbMetricsExporterProperties.class})
	public static class KairosdbMetricsExportConfig {

		@Bean
		public KairosdbMetricsExporter kairosdbMetricsExporter(EventPublisher eventPublisher, KairosdbMetricsExporterProperties properties) throws Exception {
			return new KairosdbMetricsExporter(eventPublisher, properties);
		}
	}

	@Bean
	@ConditionalOnMissingBean(MetricsExporter.class)
	public MetricsExporter metricsExporter() {
		return new Slf4jMetricsExporter("metrics");
	}

}
