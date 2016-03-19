package lamp.monitoring.core.health.service;

import lamp.monitoring.core.alert.model.AlertRule;

import java.util.List;


public interface HealthAlertRuleProvider {

	List<AlertRule> getHealthAlertRules();

}
