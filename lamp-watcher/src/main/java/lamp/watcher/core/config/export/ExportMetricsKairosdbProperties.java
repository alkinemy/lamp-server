package lamp.watcher.core.config.export;

import lamp.watcher.core.config.base.KairosdbProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "export.metrics.kairosdb.enabled")
public class ExportMetricsKairosdbProperties extends KairosdbProperties {

}
