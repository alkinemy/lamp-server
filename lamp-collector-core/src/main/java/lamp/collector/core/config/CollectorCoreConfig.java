package lamp.collector.core.config;

import lamp.collector.core.config.export.ExporterConfig;
import lamp.collector.core.service.health.HealthAsyncProcessorService;
import lamp.collector.core.service.health.HealthLoadService;
import lamp.collector.core.service.health.HealthProcessFacadeService;
import lamp.collector.core.service.health.HealthProcessService;
import lamp.collector.core.service.metrics.MetricsAsyncProcessorService;
import lamp.collector.core.service.metrics.MetricsLoadService;
import lamp.collector.core.service.metrics.MetricsProcessFacadeService;
import lamp.collector.core.service.metrics.MetricsProcessService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CollectionSchedulingConfig.class, ExporterConfig.class})
public class CollectorCoreConfig {

	@Bean
	@ConditionalOnMissingBean
	public HealthLoadService healthLoadService() {
		return new HealthLoadService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthProcessService healthProcessService() {
		return new HealthProcessService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthAsyncProcessorService healthAsyncProcessorService() {
		return new HealthAsyncProcessorService();
	}

	@Bean
	@ConditionalOnMissingBean
	public HealthProcessFacadeService healthProcessFacadeService() {
		return new HealthProcessFacadeService();
	}


	@Bean
	@ConditionalOnMissingBean
	public MetricsLoadService metricsLoadService() {
		return new MetricsLoadService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsProcessService metricsProcessService() {
		return new MetricsProcessService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsAsyncProcessorService metricsAsyncProcessorService() {
		return new MetricsAsyncProcessorService();
	}

	@Bean
	@ConditionalOnMissingBean
	public MetricsProcessFacadeService metricsProcessFacadeService() {
		return new MetricsProcessFacadeService();
	}

}
