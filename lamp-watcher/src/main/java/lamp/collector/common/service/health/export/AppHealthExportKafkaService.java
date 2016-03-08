package lamp.collector.common.service.health.export;

import lamp.collector.common.config.base.KafkaProducerProperties;
import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.MonitoringTarget;
import lamp.collector.common.support.kafka.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Slf4j
public class AppHealthExportKafkaService implements AppHealthExportService {

	private Producer<String, MonitoringTarget> producer;
	private String topic;

	public AppHealthExportKafkaService(KafkaProducerProperties kafkaProperties) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());

		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.internals.DefaultPartitioner");

		this.producer = new KafkaProducer<>(props);
		this.topic = kafkaProperties.getTopic();
	}


	@Override
	public void export(MonitoringTarget app, AppHealth health) {
		String key = new StringBuilder().append(app.getId()).append('-').append(health.getTimestamp()).toString();

		ProducerRecord<String, MonitoringTarget> data = new ProducerRecord(topic, key, health);
		producer.send(data, (metadata, exception) -> {
			if (exception != null) {
				log.warn("Metrics Export failed ", exception);
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