package lamp.server.watch.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "metrics.export.kafka")
public class KafkaProperties {

	private String topic;

	private String bootstrapServers;
	private String clientId;

}
