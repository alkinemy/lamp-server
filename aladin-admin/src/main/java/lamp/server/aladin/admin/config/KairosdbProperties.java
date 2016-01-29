package lamp.server.aladin.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kairosdb")
public class KairosdbProperties {

	private String url;

}
