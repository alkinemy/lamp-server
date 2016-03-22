package lamp.monitoring.metrics.kairosdb.service;

import lamp.common.collector.model.MetricsTarget;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.monitoring.core.alert.service.operator.GreaterThanOperator;
import lamp.monitoring.core.metrics.service.MetricsAlertRuleProvider;
import lamp.monitoring.metrics.kairosdb.model.KairosdbAlertRuleExpression;
import lamp.monitoring.metrics.kairosdb.model.KairosdbMetricsAlertRule;
import lamp.support.kairosdb.KairosdbClient;
import lamp.support.kairosdb.KairosdbProperties;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KairosdbMetricsMonitoringProcessorTest {

	@Test
	public void testWatch() throws Exception {
		MetricsAlertRuleProvider metricsAlertRuleProvider = new MetricsAlertRuleProvider() {

			@Override
			public <R extends AlertRule> List<R> getMetricsAlertRules(Class<R> ruleClass) {
				KairosdbMetricsAlertRule metricsAlarmDefinition = new KairosdbMetricsAlertRule();
				KairosdbAlertRuleExpression kairosdbAlarmExpression = new KairosdbAlertRuleExpression();
				kairosdbAlarmExpression.setMetric("server.mem.used");
				kairosdbAlarmExpression.setGrouperTagNames(new String[] {"id"});
				kairosdbAlarmExpression.setOperator(new GreaterThanOperator());
				kairosdbAlarmExpression.setThreshold("20660962304");
//				kairosdbAlarmExpression.setFunctions(new KairosdbFunction[] {KairosdbFunction.RATE_SECOND});
				metricsAlarmDefinition.setExpression(kairosdbAlarmExpression);
				List<R> rules = new ArrayList<>();
				rules.add((R) metricsAlarmDefinition);
				return rules;
			}
		};

		KairosdbProperties properties = new KairosdbProperties() {
			@Override public String getUrl() {
				return "http://localhost:8080";
			}
		};
		KairosdbClient kairosdbClient = new KairosdbClient(properties);
		AlertEventProducer alertEventProducer = new AlertEventProducer() {
			@Override public void send(AlertEvent alarmEvent) {
				System.out.println("alarmEvent = " + alarmEvent);
			}
		};
		KairosdbMetricsMonitoringProcessor processor = new KairosdbMetricsMonitoringProcessor(metricsAlertRuleProvider, kairosdbClient, alertEventProducer);

		MetricsTarget metricsTarget = new MetricsTarget() {
			@Override public String getId() {
				return "dc386fc0-1319-4a7d-b218-07b5c55784ff";
			}

			@Override public String getName() {
				return null;
			}

			@Override public String getHostname() {
				return null;
			}

			@Override public String getAddress() {
				return null;
			}

			@Override public String getGroupId() {
				return null;
			}


			@Override public String getMetricsType() {
				return null;
			}

			@Override public String getMetricsUrl() {
				return null;
			}

			@Override public String getMetricsExportPrefix() {
				return null;
			}

			@Override public Map<String, String> getTags() {
				return null;
			}

			@Override
			public boolean isMetricsCollectionEnabled() {
				return true;
			}
		};
		processor.monitoring(metricsTarget);

	}
}