package lamp.collector.core.service.metrics;

import lamp.collector.core.config.CollectionMetricsKafkaProperties;
import lamp.collector.core.model.MetricsAndTags;
import lamp.common.collector.model.TargetMetrics;
import lamp.common.utils.StringUtils;
import lamp.support.kafka.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MetricsKafkaCollectionService extends KafkaConsumer<String, MetricsAndTags> {

	@Autowired
	private MetricsProcessorService metricsProcessorService;

	public MetricsKafkaCollectionService(CollectionMetricsKafkaProperties kafkaConsumerProperties) {
		super(kafkaConsumerProperties, MetricsAndTags.class, StringUtils.split(kafkaConsumerProperties.getTopics(), ","));
		log.info("MetricsKafkaCollectionService Init");
	}

	@Override protected void handleConsumerRecord(ConsumerRecord<String, MetricsAndTags> record) {
		log.debug("record = {}", record.value());
		MetricsAndTags metricsAndTags = record.value();

		TargetMetrics targetMetrics = new TargetMetrics(metricsAndTags.getTimestamp(), metricsAndTags.getMetrics(), metricsAndTags.getTags());

		metricsProcessorService.process(null, targetMetrics, null);
	}

}
