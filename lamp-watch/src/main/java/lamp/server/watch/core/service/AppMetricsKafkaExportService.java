package lamp.server.watch.core.service;

import lamp.server.watch.core.config.KafkaProperties;
import lamp.server.watch.core.domain.WatchedApp;
import lamp.server.watch.core.domain.WatchedAppMetrics;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PreDestroy;
import java.util.Properties;

public class AppMetricsKafkaExportService implements AppMetricsExportService {

	private String topic;
	private Producer<String, WatchedApp> producer;

	public AppMetricsKafkaExportService(KafkaProperties kafkaProperties) {
		this.topic = kafkaProperties.getTopic();

		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
		props.put("client.id", kafkaProperties.getClientId());

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "lamp.server.watch.core.support.kafka.JsonSerializer");
		props.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");

		this.producer = new KafkaProducer<>(props);
	}


	@Override public void exportMetrics(WatchedAppMetrics watchedAppMetrics) {
		String key = new StringBuilder().append(watchedAppMetrics.getId()).append('-').append(watchedAppMetrics.getTimestamp()).toString();

		ProducerRecord<String, WatchedApp> data = new ProducerRecord(topic, key, watchedAppMetrics);
		producer.send(data);
	}

	@PreDestroy
	public void close() {
		if (producer != null) {
			producer.close();
		}
	}
}