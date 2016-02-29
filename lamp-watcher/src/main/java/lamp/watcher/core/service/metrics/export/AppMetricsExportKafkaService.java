package lamp.watcher.core.service.metrics.export;

import lamp.watcher.core.config.base.KafkaProducerProperties;
import lamp.watcher.core.domain.AppMetrics;
import lamp.watcher.core.domain.WatchedApp;
import lamp.watcher.core.support.kafka.JsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Slf4j
public class AppMetricsExportKafkaService implements AppMetricsExportService {

	private Producer<String, WatchedApp> producer;
	private String topic;

	public AppMetricsExportKafkaService(KafkaProducerProperties kafkaProperties) {
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
	public void export(WatchedApp app, AppMetrics metrics) {
		String key = new StringBuilder().append(app.getId()).append('-').append(metrics.getTimestamp()).toString();

		ProducerRecord<String, WatchedApp> data = new ProducerRecord(topic, key, metrics);
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