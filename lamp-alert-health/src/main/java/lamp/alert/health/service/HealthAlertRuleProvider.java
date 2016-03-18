package lamp.alert.health.service;

import lamp.alert.core.model.AlertRule;

import java.util.List;


public interface HealthAlertRuleProvider {

	List<AlertRule> getHealthAlertRules();

}
