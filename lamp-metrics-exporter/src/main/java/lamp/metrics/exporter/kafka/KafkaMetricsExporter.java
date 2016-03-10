package lamp.metrics.exporter.kafka;

import lamp.support.kafka.KafkaProducerProperties;
import lamp.support.kafka.serialization.JsonSerializer;
import lamp.common.metrics.MetricsExporter;
import lamp.common.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Slf4j
public class KafkaMetricsExporter implements MetricsExporter {

	private Producer<String, TargetMetrics> producer;
	private String topic;

	public KafkaMetricsExporter(KafkaProducerProperties kafkaProperties) {
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
	public void export(TargetMetrics targetMetrics) {
		String key = new StringBuilder().append(targetMetrics.getId()).append('-').append(targetMetrics.getTimestamp()).toString();

		ProducerRecord<String, TargetMetrics> data = new ProducerRecord(topic, key, targetMetrics);
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