package lamp.watcher.core.config.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
public class KairosdbProperties {

	private String url;

}
