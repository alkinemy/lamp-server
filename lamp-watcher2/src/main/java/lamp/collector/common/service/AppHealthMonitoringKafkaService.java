package lamp.collector.common.service;

import lamp.common.utils.StringUtils;
import lamp.collector.common.config.base.KafkaConsumerProperties;
import lamp.collector.common.domain.AppHealth;
import lamp.collector.common.domain.MonitoringTarget;
import lamp.collector.common.service.health.AppHealthMonitoringService;
import lamp.collector.common.support.kafka.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class AppHealthMonitoringKafkaService extends KafkaConsumer<String, AppHealth> {

	@Autowired
	private MonitoringTargetService watchedAppService;

	@Autowired
	private AppHealthMonitoringService appHealthMonitoringService;

	public AppHealthMonitoringKafkaService(KafkaConsumerProperties kafkaProperties) {
		super(kafkaProperties, AppHealth.class, StringUtils.split(kafkaProperties.getTopics(), ","));
	}

	@Override
	protected void handleConsumerRecord(ConsumerRecord<String, AppHealth> record) {
		AppHealth appHealth = record.value();
		log.info("appHealth = {}, {}", record.key(), appHealth);

		String id = appHealth.getId();
		Optional<MonitoringTarget> watchedAppOptional = watchedAppService.getWatchedAppOptional(id);
		MonitoringTarget watchedApp = watchedAppOptional.orElseGet(() -> createWatchedApp(appHealth));

		appHealthMonitoringService.monitoring(watchedApp, appHealth);
	}

	protected MonitoringTarget createWatchedApp(AppHealth appHealth) {
		MonitoringTarget watchedApp = new MonitoringTarget();
		// TODO 구현

		watchedApp.setId(appHealth.getId());
		watchedApp.setName(appHealth.getName());

		Map<String, String> tags = appHealth.getTags();
		if (tags != null) {
			watchedApp.setHostname(tags.get("hostname"));
			watchedApp.setGroupId(tags.get("groupId"));
			watchedApp.setArtifactId(tags.get("artifactId"));
			watchedApp.setVersion(tags.get("version"));
		}

		return watchedApp;
	}

	@PreDestroy
	public void close() {
		super.close();
	}
}
