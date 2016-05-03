package lamp.watcher.service;

import lamp.collector.core.model.MetricsAndTags;
import lamp.collector.core.service.metrics.MetricsProcessorService;
import lamp.common.collector.model.TargetMetrics;
import lamp.common.utils.StringUtils;
import lamp.support.kafka.consumer.KafkaConsumer;
import lamp.watcher.config.MetricsKafkaConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MetricsKafkaConsumeService extends KafkaConsumer<String, MetricsAndTags> {

	@Autowired
	private MetricsProcessorService metricsProcessorService;

	public MetricsKafkaConsumeService(MetricsKafkaConsumerProperties kafkaConsumerProperties) {
		super(kafkaConsumerProperties, MetricsAndTags.class, StringUtils.split(kafkaConsumerProperties.getTopics(), ","));
	}

	@Override protected void handleConsumerRecord(ConsumerRecord<String, MetricsAndTags> record) {
		log.debug("record = {}", record.value());
		MetricsAndTags metricsAndTags = record.value();

		TargetMetrics targetMetrics = new TargetMetrics(metricsAndTags.getTimestamp(), metricsAndTags.getMetrics(), metricsAndTags.getTags());

		metricsProcessorService.process(null, targetMetrics, null);
	}

}
