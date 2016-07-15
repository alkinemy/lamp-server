package lamp.collector.core.metrics.processor;

import lamp.collector.core.config.CollectionMetricsKafkaProperties;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.common.utils.StringUtils;
import lamp.support.kafka.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class KafkaTargetMetricsProcessor extends KafkaConsumer<String, TargetMetrics> {

	private final TargetMetricsProcessor targetMetricsProcessor;

	public KafkaTargetMetricsProcessor(CollectionMetricsKafkaProperties kafkaConsumerProperties, TargetMetricsProcessor targetMetricsProcessor) {
		super(kafkaConsumerProperties, TargetMetrics.class, StringUtils.split(kafkaConsumerProperties.getTopics(), ","));

		this.targetMetricsProcessor = targetMetricsProcessor;
	}

	@Override protected void handleConsumerRecord(ConsumerRecord<String, TargetMetrics> record) {
		log.debug("record = {}", record.value());
		TargetMetrics targetMetrics = record.value();

		targetMetricsProcessor.process(targetMetrics);
	}

}
