package lamp.collector.core.metrics.handler.exporter.kafka;

import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.handler.exporter.AbstractTargetMetricsExporter;
import lamp.support.kafka.KafkaProducerProperties;
import lamp.support.kafka.serialization.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.util.Properties;

@Slf4j
public class TargetMetricsKafkaExporter extends AbstractTargetMetricsExporter implements Closeable {

	private Producer<String, TargetMetrics> producer;
	private String topic;

	public TargetMetricsKafkaExporter(KafkaProducerProperties kafkaProperties) {
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
	public void doHandle(TargetMetrics targetMetrics) {
		log.debug("Kafka Metrics Export : {}", targetMetrics);

		String key = new StringBuilder().append(targetMetrics.getId()).append('-').append(targetMetrics.getTimestamp()).toString();

		ProducerRecord<String, TargetMetrics> data = new ProducerRecord(topic, key, targetMetrics);
		producer.send(data, (metadata, exception) -> {
			if (exception != null) {
				handleException(targetMetrics, exception, metadata);
			}
		});
	}

	@Override
	public void close() {
		if (producer != null) {
			producer.close();
		}
	}

}