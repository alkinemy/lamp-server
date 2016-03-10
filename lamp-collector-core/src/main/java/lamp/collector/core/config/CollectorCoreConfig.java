package lamp.collector.core.config;

import lamp.collector.core.config.export.ExporterConfig;
import lamp.collector.core.service.health.HealthCollectionService;
import lamp.collector.core.service.health.HealthExportService;
import lamp.collector.core.service.health.HealthLoadService;
import lamp.collector.core.service.metrics.MetricsCollectionService;
import lamp.collector.core.service.metrics.MetricsExportService;
import lamp.collector.core.service.metrics.MetricsLoadService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CollectionSchedulingConfig.class, ExporterConfig.class})
public class CollectorCoreConfig {

	@Bean
	@ConditionalOnMissingBean
	public HealthLoadService healthLoaderService() {
		return new HealthLoadService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthExportService healthExportService() {
		return new HealthExportService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthCollectionService healthCollectionService() {
		return new HealthCollectionService();
	}


	@Bean
	@ConditionalOnMissingBean
	public MetricsLoadService metricsLoaderService() {
		return new MetricsLoadService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsExportService metricsExporterService() {
		return new MetricsExportService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsCollectionService metricsCollectionService() {
		return new MetricsCollectionService();
	}

}
