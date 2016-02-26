package lamp.watch.server.core.service;

import lamp.watch.server.core.config.KafkaProperties;
import lamp.watch.server.core.domain.WatchedApp;
import lamp.watch.server.core.domain.WatchedAppMetrics;
import lamp.watch.server.core.support.kafka.JsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.PreDestroy;
import java.util.Properties;

public class AppMetricsKafkaExportService implements AppMetricsExportService {

	private String topic;
	private Producer<String, WatchedApp> producer;

	public AppMetricsKafkaExportService(KafkaProperties kafkaProperties) {
		this.topic = kafkaProperties.getTopic();

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());

		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.internals.DefaultPartitioner");

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