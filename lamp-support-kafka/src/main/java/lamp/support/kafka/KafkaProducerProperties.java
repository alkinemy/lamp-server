package lamp.support.kafka;

public interface KafkaProducerProperties {

	String getBootstrapServers();
	String getClientId();

	String getTopic();

	long getMaxBlockMs();

}
