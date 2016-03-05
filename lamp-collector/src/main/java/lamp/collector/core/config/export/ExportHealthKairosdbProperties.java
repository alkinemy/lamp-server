package lamp.collector.core.config.export;

import lamp.collector.core.CollectorConstants;
import lamp.collector.core.config.CollectorConfig;
import lamp.collector.core.config.base.KairosdbProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.EXPORT_HEALTH_KAIROSDB_PREFIX)
public class ExportHealthKairosdbProperties extends KairosdbProperties {

}
