package lamp.collector.core.config.export;

import lamp.collector.core.config.base.KairosdbProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "export.health.kairosdb")
public class ExportHealthKairosdbProperties extends KairosdbProperties {

}
