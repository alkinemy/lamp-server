package lamp.support.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SimpleKafkaProducerProperties implements KafkaProducerProperties {

	private String bootstrapServers;
	private String clientId;

	private String topic;

}
