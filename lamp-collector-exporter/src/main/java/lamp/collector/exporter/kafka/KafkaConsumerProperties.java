package lamp.collector.exporter.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaConsumerProperties {

	private String bootstrapServers;

	private String groupId;

	private boolean enableAutoCommit;
	private long autoCommitIntervalMs = 1000;
	private long sessionTimeoutMs = 30000;

	private String topics;

}
