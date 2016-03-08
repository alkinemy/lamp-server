package lamp.collector.app.core.config.export;

import lamp.collector.app.core.CollectorConstants;
import lamp.support.kairosdb.KairosdbProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.EXPORT_HEALTH_KAIROSDB_PREFIX)
public class ExportHealthKairosdbProperties extends KairosdbProperties {

}
