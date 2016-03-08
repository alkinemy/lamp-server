package lamp.watcher.monitoring.kafka.service;

import lamp.common.collection.metrics.TargetMetrics;
import lamp.common.monitoring.MonitoringTarget;
import lamp.common.utils.StringUtils;
import lamp.support.kafka.KafkaConsumerProperties;
import lamp.support.kafka.consumer.KafkaConsumer;
import lamp.watcher.core.service.MonitoringMetricsService;
import lamp.watcher.core.service.MonitoringTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MonitoringMetricsKafkaService extends KafkaConsumer<String, TargetMetrics> {

	@Autowired
	private MonitoringTargetService monitoringTargetService;

	@Autowired
	private MonitoringMetricsService monitoringMetricsService;

	public MonitoringMetricsKafkaService(KafkaConsumerProperties kafkaProperties) {
		super(kafkaProperties, TargetMetrics.class, StringUtils.split(kafkaProperties.getTopics(), ","));
	}

	@Override
	protected void handleConsumerRecord(ConsumerRecord<String, TargetMetrics> record) {
		TargetMetrics targetMetrics = record.value();
		log.debug("targetMetrics = {}, {}", record.key(), targetMetrics);

		String id = targetMetrics.getId();
		Optional<MonitoringTarget> monitoringTargetOptional = monitoringTargetService.getMonitoringTargetOptional(id);
		MonitoringTarget monitoringTarget = monitoringTargetOptional.orElseGet(() -> createMonitoringTarget(targetMetrics));

		monitoringMetricsService.monitoring(monitoringTarget, targetMetrics);
	}

	protected MonitoringTarget createMonitoringTarget(TargetMetrics targetMetrics) {
		return monitoringTargetService.createMonitoringTarget(targetMetrics);
	}

	@PreDestroy
	public void close() {
		super.close();
	}

}
