package lamp.support.kafka;

public interface KafkaConsumerProperties {

	String getBootstrapServers();

	String getGroupId();

	boolean isEnableAutoCommit();
	long getAutoCommitIntervalMs();
	long getSessionTimeoutMs();

	String getTopics();

}
