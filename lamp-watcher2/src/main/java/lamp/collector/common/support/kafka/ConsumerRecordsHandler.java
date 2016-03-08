package lamp.collector.common.support.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;

public interface ConsumerRecordsHandler<K, V> {

	void handleConsumerRecords(ConsumerRecords<K, V> records);

}
