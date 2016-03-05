package lamp.collector.core.config.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaProducerProperties {

	private String bootstrapServers;
	private String clientId;

	private String topic;

}
