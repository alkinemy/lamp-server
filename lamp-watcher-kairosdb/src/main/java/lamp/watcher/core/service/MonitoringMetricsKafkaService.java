package lamp.watcher.core.service;

import lamp.common.collector.TargetMetrics;
import lamp.common.utils.StringUtils;
import lamp.metrics.exporter.kairosdb.KairosdbMetricsExporter;
import lamp.support.kafka.KafkaConsumerProperties;
import lamp.support.kafka.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;

@Slf4j
public class MonitoringMetricsKafkaService extends KafkaConsumer<String, TargetMetrics> {

	@Autowired
	private KairosdbMetricsExporter kairosdbMetricsExporter;

	public MonitoringMetricsKafkaService(KafkaConsumerProperties kafkaProperties) {
		super(kafkaProperties, TargetMetrics.class, StringUtils.split(kafkaProperties.getTopics(), ","));
	}

	@Override
	protected void handleConsumerRecord(ConsumerRecord<String, TargetMetrics> record) {
		TargetMetrics targetMetrics = record.value();
		log.debug("targetMetrics = {}, {}", record.key(), targetMetrics);

//		kairosdbMetricsExporter.process(targetMetrics);
	}

	@PreDestroy
	public void close() {
		super.close();
	}

}
