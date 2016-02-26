package lamp.watch.server.core.service;

import org.junit.Test;

import static org.junit.Assert.*;


public class KafkaToKairosDbServiceTest {

	@Test
	public void testHandleConsumerRecord() throws Exception {
		KafkaToKairosDbService kafkaToKairosDbService = new KafkaToKairosDbService();
		kafkaToKairosDbService.init();

		Thread.sleep(60 * 1000);

		kafkaToKairosDbService.close();
	}
}