package lamp.monitoring.core.alert.service;

import lamp.monitoring.core.alert.model.AlertRule;

public interface AlertRuleService {
    AlertRule<?> getAlertRule(String ruleId);
}
