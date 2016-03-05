package lamp.collector.core.support.kafka;

import lamp.collector.core.config.base.KafkaConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class KafkaConsumer<K, V> {

	private ExecutorService executor;
	private KafkaConsumerRunner kafkaConsumerRunner;

	public KafkaConsumer(KafkaConsumerProperties kafkaProperties, Class<V> valueCLass, String... topics) {
		Properties props = new Properties();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(kafkaProperties.isEnableAutoCommit()));
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, String.valueOf(kafkaProperties.getAutoCommitIntervalMs()));
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, String.valueOf(kafkaProperties.getSessionTimeoutMs()));
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
		props.put(JsonDeserializer.VALUE_TYPE, valueCLass);

		List<String> topicList = Arrays.asList(topics);

		kafkaConsumerRunner = new KafkaConsumerRunner<>(props, topicList, this::handleConsumerRecords);

		executor = Executors.newSingleThreadExecutor();
		executor.execute(kafkaConsumerRunner);
	}

	protected void handleConsumerRecords(ConsumerRecords<K, V> records) {
		records.forEach(this::handleConsumerRecord);
	}

	protected abstract void handleConsumerRecord(ConsumerRecord<K, V> record);

	public void close() {
		kafkaConsumerRunner.shutdown();

		executor.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				executor.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!executor.awaitTermination(60, TimeUnit.SECONDS))
					log.error("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			executor.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
}
