package lamp.monitoring.core.metrics.model;

import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.monitoring.metrics.rule.spel.SpelTargetMetricsAlertRule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class TargetMetricsAlertRuleTest {

	@Test
	public void testIsAlertTarget() throws Exception {
		SpelTargetMetricsAlertRule rule = new SpelTargetMetricsAlertRule();
		rule.setTargetExpression("${id == 'skynet'}");

		MonitoringTargetMetrics target = new MonitoringTargetMetrics();
		target.setId("skynet");

		assertThat(rule.isAlertTarget(target)).isTrue();
	}

	@Test
	public void testIsAlertTarget_Tags() throws Exception {
		SpelTargetMetricsAlertRule rule = new SpelTargetMetricsAlertRule();
		rule.setTargetExpression("${tags[hostName] == 'skynet'}");

		MonitoringTargetMetrics target = new MonitoringTargetMetrics();
		target.setId("skynet");
		Map<String, String> tags = new HashMap<>();
		tags.put("hostName", "bluesky");
		target.setTags(tags);

		assertThat(rule.isAlertTarget(target)).isTrue();
	}

}