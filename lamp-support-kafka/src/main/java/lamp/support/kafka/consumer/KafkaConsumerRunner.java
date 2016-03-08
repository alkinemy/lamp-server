package lamp.support.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;


public class KafkaConsumerRunner<K, V> implements Runnable {

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private final KafkaConsumer consumer;

	private final List<String> topics;

	private final ConsumerRecordsHandler consumerRecordsHandler;

	public KafkaConsumerRunner(Properties props, List<String> topics, ConsumerRecordsHandler<K, V> consumerRecordsHandler) {
		this.consumer = new KafkaConsumer<>(props);
		this.topics = topics;
		this.consumerRecordsHandler = consumerRecordsHandler;
	}

	@Override
	public void run() {
		try {
			consumer.subscribe(topics);
			while (!closed.get()) {
				ConsumerRecords<K, V> records = consumer.poll(10000);
				// Handle new records
				consumerRecordsHandler.handleConsumerRecords(records);
			}
		} catch (WakeupException e) {
			// Ignore exception if closing
			if (!closed.get()) throw e;
		} finally {
			consumer.close();
		}
	}

	// Shutdown hook which can be called from a separate thread
	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
	}

}
