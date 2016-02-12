package lamp.server.aladin.admin.service.metrics;

import lamp.server.aladin.admin.config.metrics.KafkaProperties;
import lamp.server.aladin.core.domain.AgentMetrics;
import lamp.server.aladin.core.service.MetricsExportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Slf4j
public class MetricsExportKafkaService implements MetricsExportService {

	private final KafkaProperties kafkaProperties;

	private String topic;
	private Producer<String, AgentMetrics> producer;

	public MetricsExportKafkaService(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;

		this.topic = kafkaProperties.getTopic();

		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
		props.put("client.id", kafkaProperties.getClientId());

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "lamp.server.aladin.admin.support.kafka.JsonSerializer");
		props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");

		this.producer = new KafkaProducer<>(props);
	}

	@Override
	@Async
	public void exportMetrics(AgentMetrics agentMetrics) {
		try {

			String key = new StringBuilder().append(agentMetrics.getId()).append('-').append(agentMetrics.getTimestamp()).toString();

			ProducerRecord<String, AgentMetrics> data = new ProducerRecord(topic, key, agentMetrics);
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
