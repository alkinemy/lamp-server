package lamp.watcher.core.config.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
