package lamp.admin.web.service.metrics;

import lamp.admin.core.monitoring.domain.TargetServerMetrics;
import lamp.admin.core.monitoring.service.MetricsExportService;
import lamp.admin.web.config.metrics.KafkaProperties;
import lamp.admin.web.support.kafka.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Slf4j
public class MetricsExportKafkaService implements MetricsExportService {

	private final KafkaProperties kafkaProperties;

	private String topic;
	private Producer<String, TargetServerMetrics> producer;

	public MetricsExportKafkaService(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;

		this.topic = kafkaProperties.getTopic();

		Properties props = new Properties();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());

		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
//		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.internals.DefaultPartitioner");

		log.info("Kafka Properties : {}", props);
		this.producer = new KafkaProducer<>(props);
	}

	@Override
	@Async
	public void exportMetrics(TargetServerMetrics agentMetrics) {
		try {

			String key = new StringBuilder().append(agentMetrics.getId()).append('-').append(agentMetrics.getTimestamp()).toString();

			ProducerRecord<String, TargetServerMetrics> data = new ProducerRecord(topic, key, agentMetrics);
			producer.send(data);

		} catch (Exception e) {
			log.warn("Export Metrics failed", e);
		}
	}

	@PreDestroy
	public void close() {
		if (producer != null) {
			producer.close();
		}
	}

}
