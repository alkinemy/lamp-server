package lamp.watcher.core.config.export;

import lamp.watcher.core.service.health.export.AppHealthExportKafkaService;
import lamp.watcher.core.service.metrics.export.AppMetricsExportKafkaService;
import lamp.watcher.core.service.metrics.export.AppMetricsExportKairosdbService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportConfig {

	@ConditionalOnProperty(name = "export.health.kafka.enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportHealthKafkaProperties.class})
	public static class ExportHealthKafkaConfig {
		@Bean
		public AppHealthExportKafkaService appMetricsExportKafkaService(ExportHealthKafkaProperties properties) {
			return new AppHealthExportKafkaService(properties);
		}
	}

	@ConditionalOnProperty(name = "export.metrics.kafka.enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportMetricsKafkaProperties.class})
	public static class ExportMetricsKafkaConfig {
		@Bean
		public AppMetricsExportKafkaService appMetricsExportKafkaService(ExportMetricsKafkaProperties properties) {
			return new AppMetricsExportKafkaService(properties);
		}
	}


	@ConditionalOnProperty(name = "export.metrics.kairosdb.enabled", havingValue = "true")
	@EnableConfigurationProperties({ ExportHealthKairosdbProperties.class})
	public static class ExportMetricsKairosdbConfig {

		@Bean
		public AppMetricsExportKairosdbService AppMetricsExportKairosdbService(ExportMetricsKairosdbProperties properties) throws Exception {
			return new AppMetricsExportKairosdbService(properties);
		}

	}
}
