package lamp.collector.core.config;

import lamp.collector.core.config.export.ExporterConfig;
import lamp.collector.core.service.health.HealthProcessorAsyncSupportService;
import lamp.collector.core.service.health.HealthLoaderService;
import lamp.collector.core.service.health.HealthProcessService;
import lamp.collector.core.service.health.HealthProcessorService;
import lamp.collector.core.service.metrics.MetricsProcessorAsyncSupportService;
import lamp.collector.core.service.metrics.MetricsLoaderService;
import lamp.collector.core.service.metrics.MetricsProcessService;
import lamp.collector.core.service.metrics.MetricsProcessorService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CollectionSchedulingConfig.class, ExporterConfig.class})
public class CollectorCoreConfig {

	@Bean
	@ConditionalOnMissingBean
	public HealthLoaderService healthLoaderService() {
		return new HealthLoaderService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthProcessorService healthProcessorService() {
		return new HealthProcessorService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthProcessorAsyncSupportService healthProcessorAsyncSupportService() {
		return new HealthProcessorAsyncSupportService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthProcessService healthProcessService() {
		return new HealthProcessService();
	}


	@Bean
	@ConditionalOnMissingBean
	public MetricsLoaderService metricsLoaderService() {
		return new MetricsLoaderService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsProcessorService metricsProcessorService() {
		return new MetricsProcessorService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsProcessorAsyncSupportService metricsProcessorAsyncSupportService() {
		return new MetricsProcessorAsyncSupportService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsProcessService metricsProcessService() {
		return new MetricsProcessService();
	}

}
