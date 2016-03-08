package lamp.collector.app.core.config.export;

import lamp.collector.app.core.CollectorConstants;
import lamp.common.collection.health.HealthExporter;
import lamp.common.collection.metrics.MetricsExporter;
import lamp.collector.exporter.kafka.KafkaHealthExporter;
import lamp.collector.exporter.kafka.KafkaMetricsExporter;
import lamp.collector.exporter.kairosdb.KairosdbMetricsExporter;
import lamp.collector.exporter.slf4j.Slf4jHealthExporter;
import lamp.collector.exporter.slf4j.Slf4jMetricsExporter;
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
		public KafkaHealthExporter kafkaHealthExporter(ExportHealthKafkaProperties properties) {
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
	@EnableConfigurationProperties({ ExportMetricsKafkaProperties.class})
	public static class ExportMetricsKafkaConfig {
		@Bean
		public KafkaMetricsExporter kafkaMetricsExporter(ExportMetricsKafkaProperties properties) {
			return new KafkaMetricsExporter(properties);
		}
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportHealthKairosdbProperties.class})
	public static class ExportMetricsKairosdbConfig {

		@Bean
		public KairosdbMetricsExporter kairosdbMetricsExporter(ExportMetricsKairosdbProperties properties) throws Exception {
			return new KairosdbMetricsExporter(properties);
		}
	}

	@Bean
	@ConditionalOnMissingBean(MetricsExporter.class)
	public MetricsExporter metricsExporter() {
		return new Slf4jMetricsExporter("metrics");
	}

}
