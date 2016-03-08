package lamp.collector.common.service;

import lamp.common.utils.StringUtils;
import lamp.collector.common.config.base.KafkaConsumerProperties;
import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.WatchedApp;
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
	private WatchedAppService watchedAppService;

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
		Optional<WatchedApp> watchedAppOptional = watchedAppService.getWatchedAppOptional(id);
		WatchedApp watchedApp = watchedAppOptional.orElseGet(() -> createWatchedApp(appMetrics));

		appMetricsMonitoringService.monitoring(watchedApp, appMetrics);
	}

	protected WatchedApp createWatchedApp(AppMetrics appMetrics) {
		WatchedApp watchedApp = new WatchedApp();
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
