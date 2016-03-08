package lamp.collector.common.config.export;

import lamp.collector.common.config.base.KairosdbProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "export.health.kairosdb")
public class ExportHealthKairosdbProperties extends KairosdbProperties {

}
