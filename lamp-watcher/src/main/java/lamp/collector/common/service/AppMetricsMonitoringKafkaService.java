package lamp.collector.common.service;

import lamp.common.utils.StringUtils;
import lamp.collector.common.config.base.KafkaConsumerProperties;
import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.MonitoringTarget;
import lamp.collector.common.service.metrics.AppMetricsMonitoringService;
import lamp.collector.common.support.kafka.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.*;

@Slf4j
public class AppMetricsMonitoringKafkaService extends KafkaConsumer<String, AppMetrics> {

	@Autowired
	private MonitoringTargetService watchedAppService;

	@Autowired
	private AppMetricsMonitoringService appMetricsMonitoringService;

	public AppMetricsMonitoringKafkaService(KafkaConsumerProperties kafkaProperties) {
		super(kafkaProperties, AppMetrics.class, StringUtils.split(kafkaProperties.getTopics(), ","));
	}

	@Override
	protected void handleConsumerRecord(ConsumerRecord<String, AppMetrics> record) {
		AppMetrics appMetrics = record.value();
		log.info("appMetrics = {}, {}", record.key(), appMetrics);

		String id = appMetrics.getId();
		Optional<MonitoringTarget> watchedAppOptional = watchedAppService.getWatchedAppOptional(id);
		MonitoringTarget watchedApp = watchedAppOptional.orElseGet(() -> createWatchedApp(appMetrics));

		appMetricsMonitoringService.monitoring(watchedApp, appMetrics);
	}

	protected MonitoringTarget createWatchedApp(AppMetrics appMetrics) {
		MonitoringTarget watchedApp = new MonitoringTarget();
		// TODO 구현

		watchedApp.setId(appMetrics.getId());
		watchedApp.setName(appMetrics.getName());

		Map<String, String> tags = appMetrics.getTags();
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
