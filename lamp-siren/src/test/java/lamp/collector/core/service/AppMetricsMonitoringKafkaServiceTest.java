package lamp.collector.core.service;

import lamp.collector.core.config.monitoring.MonitoringMetricsKafkaProperties;
import org.junit.Test;

public class AppMetricsMonitoringKafkaServiceTest {

	@Test
	public void testHandleConsumerRecords() throws Exception {
		MonitoringMetricsKafkaProperties kafkaProperties = new MonitoringMetricsKafkaProperties();
		kafkaProperties.setBootstrapServers("localhost:9092");
		kafkaProperties.setGroupId("test");
		kafkaProperties.setTopics("lamp-agent.metrics");

		AppMetricsMonitoringKafkaService appMetricsMonitoringKafkaService = new AppMetricsMonitoringKafkaService(kafkaProperties);

		Thread.sleep(60 * 1000);

		appMetricsMonitoringKafkaService.close();
	}

}