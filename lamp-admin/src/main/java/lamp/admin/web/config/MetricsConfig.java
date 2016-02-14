package lamp.admin.web.config;


import lamp.admin.web.config.metrics.KafkaProperties;
import lamp.admin.web.config.metrics.KairosdbProperties;
import lamp.admin.web.service.metrics.MetricsExportKafkaService;
import lamp.admin.web.service.metrics.MetricsExportKairosdbService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class MetricsConfig {

	@ConditionalOnProperty(name = "metrics.export.kairosdb.url")
	@EnableConfigurationProperties({ KairosdbProperties.class})
	public static class MetricsExportKairosdbConfig {

		@Bean
		public MetricsExportKairosdbService metricsToKairosdbService(KairosdbProperties kairosdbProperties) throws MalformedURLException {
			return new MetricsExportKairosdbService(kairosdbProperties);
		}

	}


	@ConditionalOnProperty(name = "metrics.export.kafka.bootstrapServers")
	@EnableConfigurationProperties({ KafkaProperties.class})
	public static class MetricsExportKafkaConfig {

		@Bean
		public MetricsExportKafkaService mtricsExportKafkaService(KafkaProperties kafkaProperties) {
			return new MetricsExportKafkaService(kafkaProperties);
		}

	}
}
