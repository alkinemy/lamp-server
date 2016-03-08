package lamp.watcher.monitoring.kafka.service;

import lamp.common.collection.health.TargetHealth;
import lamp.common.monitoring.MonitoringTarget;
import lamp.common.utils.StringUtils;
import lamp.support.kafka.KafkaConsumerProperties;
import lamp.support.kafka.consumer.KafkaConsumer;
import lamp.watcher.core.service.MonitoringHealthService;
import lamp.watcher.core.service.MonitoringTargetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.Optional;

@Slf4j
public class MonitoringHealthKafkaService extends KafkaConsumer<String, TargetHealth> {

	@Autowired
	private MonitoringTargetService monitoringTargetService;

	@Autowired
	private MonitoringHealthService monitoringHealthService;

	public MonitoringHealthKafkaService(KafkaConsumerProperties kafkaProperties) {
		super(kafkaProperties, TargetHealth.class, StringUtils.split(kafkaProperties.getTopics(), ","));
	}

	@Override
	protected void handleConsumerRecord(ConsumerRecord<String, TargetHealth> record) {
		TargetHealth targetHealth = record.value();

		String id = targetHealth.getId();
		Optional<MonitoringTarget> watchedAppOptional = monitoringTargetService.getMonitoringTargetOptional(id);
		MonitoringTarget monitoringTarget = watchedAppOptional.orElseGet(() -> createMonitoringTarget(targetHealth));

		monitoringHealthService.monitoring(monitoringTarget, targetHealth);
	}

	protected MonitoringTarget createMonitoringTarget(TargetHealth targetHealth) {
		return monitoringTargetService.createMonitoringTarget(targetHealth);
	}

	@PreDestroy
	public void close() {
		super.close();
	}

}
