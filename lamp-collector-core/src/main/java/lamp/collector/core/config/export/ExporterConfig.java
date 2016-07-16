package lamp.collector.core.config.export;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.health.handler.exporter.TargetHealthExporter;
import lamp.collector.core.health.handler.exporter.kafka.TargetHealthKafkaExporter;
import lamp.collector.core.health.handler.exporter.slf4j.Slf4JTargetHealthExporter;
import lamp.collector.core.metrics.handler.exporter.TargetMetricsExporter;
import lamp.collector.core.metrics.handler.exporter.kafka.TargetMetricsKafkaExporter;
import lamp.collector.core.metrics.handler.exporter.kairosdb.TargetMetricsKairosdbExporter;
import lamp.collector.core.metrics.handler.exporter.slf4j.Slf4jTargetMetricsExporter;
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
		public TargetHealthKafkaExporter kafkaHealthExporter(KafkaHealthExporterProperties properties) {
			return new TargetHealthKafkaExporter(properties);
		}

	}


	@Bean
	@ConditionalOnMissingBean(TargetHealthExporter.class)
	public TargetHealthExporter slf4jHealthExporter() {
		return new Slf4JTargetHealthExporter("health");
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAFKA_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ TargetMetricsKafkaExporterProperties.class})
	public static class TargetMetricsKafkaExportConfig {
		@Bean
		public TargetMetricsKafkaExporter targetMetricsKafkaExporter(TargetMetricsKafkaExporterProperties properties) {
			return new TargetMetricsKafkaExporter(properties);
		}
	}


	@ConditionalOnProperty(name = CollectorConstants.EXPORT_METRICS_KAIROSDB_PREFIX + ".enabled", havingValue = "true")
	@EnableConfigurationProperties({ TargetMetricsKairosdbExporterProperties.class})
	public static class TargetMetricsKairosdbExportConfig {

		@Bean
		public TargetMetricsKairosdbExporter targetMetricsKairosdbExporter(TargetMetricsKairosdbExporterProperties properties) throws Exception {
			return new TargetMetricsKairosdbExporter(properties);
		}
	}

	@Bean
	@ConditionalOnMissingBean(TargetMetricsExporter.class)
	public TargetMetricsExporter metricsExporter() {
		return new Slf4jTargetMetricsExporter("metrics");
	}

}
