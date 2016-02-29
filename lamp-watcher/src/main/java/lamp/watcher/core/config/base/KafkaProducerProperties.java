package lamp.watcher.core.config.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
public class KafkaProducerProperties {

	private String bootstrapServers;
	private String clientId;

	private String topic;

}
