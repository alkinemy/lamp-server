package lamp.admin.web.monitoring.service;

import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.health.model.HealthAlertRule;
import lamp.monitoring.core.health.service.HealthAlertRuleProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AlarmService implements HealthAlertRuleProvider {

	@Override public List<AlertRule> getHealthAlertRules() {
		List<AlertRule> alertRules = new ArrayList<>();
		HealthAlertRule alarmDefinition = new HealthAlertRule();
		alarmDefinition.setId("health");
		alarmDefinition.setName("Health Alert");
		alertRules.add(alarmDefinition);

		return alertRules;
	}

}
