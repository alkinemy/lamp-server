package lamp.admin.web.monitoring.service;

import lamp.admin.domain.alert.model.HostMetricsAlertRule;
import lamp.admin.domain.alert.service.AlertRuleService;
import lamp.monitoring.core.base.alert.AlertRuleProvider;
import lamp.monitoring.core.base.alert.model.AlertRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostAlertRuleService implements AlertRuleProvider {

	@Autowired
	private AlertRuleService alertRuleService;

	public Page<HostMetricsAlertRule> getHostMetricsAlertRules(Pageable pageable) {
		return alertRuleService.getAlertRules(HostMetricsAlertRule.class, pageable);
	}

	public HostMetricsAlertRule getHostMetricsAlertRule(String id) {
		return alertRuleService.getAlertRule(HostMetricsAlertRule.class, id);
	}

	public HostMetricsAlertRule insertHostMetricsAlertRule(HostMetricsAlertRule editForm) {
		return alertRuleService.createAlertRule(editForm);
	}

	public HostMetricsAlertRule updateHostMetricsAlertRule(String id, HostMetricsAlertRule editForm) {
		return alertRuleService.updateAlertRule(id, editForm);
	}

	public void deleteHostMetricsAlertRule(String id) {
		alertRuleService.deleteAlertRule(id);
	}

	@Override public List<? extends AlertRule> getAlertRules() {
		return alertRuleService.getAlertRules(HostMetricsAlertRule.class);
	}
}
