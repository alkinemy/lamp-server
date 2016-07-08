package lamp.admin.domain.alert.service;

import lamp.admin.LampAdmin;
import lamp.admin.domain.alert.model.MmsNotificationAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(LampAdmin.class)
@ActiveProfiles("test")
public class AlertActionServiceTest {

	@Autowired
	private AlertActionService alertActionService;

	@Test
	public void testCreateAlertAction() throws Exception {
		MmsNotificationAction action = new MmsNotificationAction();
		action.setId(UUID.randomUUID().toString());
		action.setName("모니터링 알람");
		action.setSubject("Monitoring Alarm");
		action.setMessage("[#{target.tags.clusterId}] #{rule.name} #{target.tags.hostName} #{state.value}");
		action.setPhoneNumbers("000-0000-0000");

		alertActionService.createAlertAction(action);
	}
}