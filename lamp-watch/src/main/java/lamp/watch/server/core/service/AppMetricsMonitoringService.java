package lamp.watch.server.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.watch.server.core.domain.AppMetrics;
import lamp.watch.server.core.domain.WatchedApp;
import lamp.watch.server.core.support.kafka.JsonDeserializer;
import lamp.watch.server.core.support.kafka.KafkaConsumerRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AppMetricsMonitoringService {


	public void monitoring(WatchedApp watchedApp, AppMetrics appMetrics) {
		if (BooleanUtils.isTrue(watchedApp.getMetricsMonitoringEnabled())) {

		}
	}
}
