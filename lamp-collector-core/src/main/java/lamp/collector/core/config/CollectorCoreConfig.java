package lamp.collector.core.config;

import lamp.collector.core.config.export.ExporterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ExporterConfig.class, CollectorAsyncExecutorConfig.class, CollectionSchedulingConfig.class})
public class CollectorCoreConfig {



}
