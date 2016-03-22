package lamp.collector.health.exporter.kafka;

import lamp.collector.core.model.EventName;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.support.kafka.KafkaProducerProperties;
import lamp.support.kafka.serialization.JsonSerializer;
import lamp.collector.health.exporter.HealthExporter;
import lamp.common.collector.model.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Slf4j
public class KafkaHealthExporter extends HealthExporter {

	private EventPublisher eventPublisher;

	private Producer<String, TargetHealth> producer;
	private String topic;

	public KafkaHealthExporter(EventPublisher eventPublisher, KafkaProducerProperties kafkaProperties) {
		this.eventPublisher = eventPublisher;

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());

		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.internals.DefaultPartitioner");

		props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, kafkaProperties.getMaxBlockMs());

		this.producer = new KafkaProducer<>(props);
		this.topic = kafkaProperties.getTopic();
	}


	@Override
	public void export(TargetHealth health) {
		log.debug("Kafka Health Export : {}", health);

		String key = new StringBuilder().append(health.getId()).append('-').append(health.getTimestamp()).toString();

		ProducerRecord<String, TargetHealth> data = new ProducerRecord(topic, key, health);
		producer.send(data, (metadata, exception) -> {
			if (exception != null) {
				eventPublisher.publish(new Event(EventLevel.WARN, EventName.HEALTH_EXPORT_TO_KAFKA_FAILED, exception, health));
			}
		});
	}

	@PreDestroy
	public void close() {
		if (producer != null) {
			producer.close();
		}
	}

}