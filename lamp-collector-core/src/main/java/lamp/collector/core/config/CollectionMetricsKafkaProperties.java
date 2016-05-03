package lamp.collector.core.config;

import lamp.support.kafka.KafkaConsumerProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
public class CollectionMetricsKafkaProperties implements KafkaConsumerProperties {

	private boolean enabled;

	private String bootstrapServers;

	private String groupId;

	private boolean enableAutoCommit = true;
	private long autoCommitIntervalMs = 1000;
	private long sessionTimeoutMs = 30000;

	private String topics;

}
