package lamp.server.aladin.admin.config.metrics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "metrics.export.kairosdb")
public class KairosdbProperties {

	private String url;

}
