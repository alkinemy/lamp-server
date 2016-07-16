package lamp.monitoring.core.metrics.kairosdb.service;

import org.junit.Test;

public class KairosdbMonitoringTargetMetricsHandlerTest {

	@Test
	public void testWatch() throws Exception {
//		TargetMetricsAlertRuleProvider metricsAlertRuleProvider = new TargetMetricsAlertRuleProvider() {
//
//			@Override
//			public <R extends AlertRule> List<R> getMetricsAlertRules(Class<R> ruleClass) {
//				KairosdbMetricsAlertRule metricsAlarmDefinition = new KairosdbMetricsAlertRule();
//				KairosdbAlertRuleExpression kairosdbAlarmExpression = new KairosdbAlertRuleExpression();
//				kairosdbAlarmExpression.setMetric("server.mem.used");
//				kairosdbAlarmExpression.setGrouperTagNames(new String[] {"id"});
//				kairosdbAlarmExpression.setOperator(new GreaterThanOperator());
//				kairosdbAlarmExpression.setThreshold("20660962304");
////				kairosdbAlarmExpression.setFunctions(new KairosdbFunction[] {KairosdbFunction.RATE_SECOND});
//				metricsAlarmDefinition.setExpression(kairosdbAlarmExpression);
//				List<R> rules = new ArrayList<>();
//				rules.add((R) metricsAlarmDefinition);
//				return rules;
//			}
//		};
//
//		KairosdbProperties properties = new KairosdbProperties() {
//			@Override public String getUrl() {
//				return "http://localhost:8080";
//			}
//		};
//		KairosdbClient kairosdbClient = new KairosdbClient(properties);
//		AlertEventProducer alertEventProducer = new AlertEventProducer() {
//			@Override public void send(AlertEvent alarmEvent) {
//				System.out.println("alarmEvent = " + alarmEvent);
//			}
//		};
//		KairosdbMetricsMonitoringProcessor processor = new KairosdbMetricsMonitoringProcessor(metricsAlertRuleProvider, kairosdbClient, alertEventProducer);
//
//		MetricsTarget metricsTarget = new MetricsTarget() {
//			@Override public String getId() {
//				return "dc386fc0-1319-4a7d-b218-07b5c55784ff";
//			}
//
//			@Override public String getName() {
//				return null;
//			}
//
//			@Override public String getHostname() {
//				return null;
//			}
//
//			@Override public String getAddress() {
//				return null;
//			}
//
//			@Override public String getGroupId() {
//				return null;
//			}
//
//			@Override public String getArtifactId() {
//				return null;
//			}
//
//			@Override public String getMetricsType() {
//				return null;
//			}
//
//			@Override public String getMetricsUrl() {
//				return null;
//			}
//
//			@Override public String getMetricsExportPrefix() {
//				return null;
//			}
//
//			@Override public Map<String, String> getTags() {
//				return null;
//			}
//
//			@Override
//			public boolean isMetricsCollectionEnabled() {
//				return true;
//			}
//		};
//		processor.monitoring(metricsTarget);

	}
}