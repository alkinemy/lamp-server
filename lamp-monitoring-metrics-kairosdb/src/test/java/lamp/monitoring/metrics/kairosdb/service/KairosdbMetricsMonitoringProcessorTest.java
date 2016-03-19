package lamp.monitoring.metrics.kairosdb.service;

import com.google.common.collect.Lists;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.monitoring.core.metrics.service.MetricsAlertRuleProvider;
import lamp.monitoring.metrics.kairosdb.model.KairosdbAlertRuleExpression;
import lamp.monitoring.core.metrics.model.MetricsAlertRule;
import lamp.common.collector.model.MetricsTarget;
import lamp.support.kairosdb.KairosdbClient;
import lamp.support.kairosdb.KairosdbProperties;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class KairosdbMetricsMonitoringProcessorTest {

	@Test
	public void testWatch() throws Exception {
		MetricsAlertRuleProvider metricsAlertRuleProvider = new MetricsAlertRuleProvider() {
			@Override public List<AlertRule> getMetricsAlertRules() {
				MetricsAlertRule metricsAlarmDefinition = new MetricsAlertRule();
				KairosdbAlertRuleExpression kairosdbAlarmExpression = new KairosdbAlertRuleExpression();
				kairosdbAlarmExpression.setMetric("server.mem.actualUsed");
				kairosdbAlarmExpression.setTagNames(new String[] {"id"});
//				kairosdbAlarmExpression.setFunctions(new KairosdbFunction[] {KairosdbFunction.RATE_SECOND});
				metricsAlarmDefinition.setExpression(kairosdbAlarmExpression);
				return Lists.newArrayList(metricsAlarmDefinition);
			}
		};
		KairosdbProperties properties = new KairosdbProperties() {
			@Override public String getUrl() {
				return "http://localhost:8081";
			}
		};
		KairosdbClient kairosdbClient = new KairosdbClient(properties);
		AlertEventProducer alertEventProducer = new AlertEventProducer() {
			@Override public void send(AlertEvent alarmEvent) {
				System.out.println("alarmEvent = " + alarmEvent);
			}
		};
		KairosdbMetricsMonitoringProcessor watcher = new KairosdbMetricsMonitoringProcessor(metricsAlertRuleProvider, kairosdbClient, alertEventProducer);

		MetricsTarget metricsTarget = new MetricsTarget() {
			@Override public String getId() {
				return "kangwooui-iMac.local";
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

			@Override public String getArtifactId() {
				return null;
			}

			@Override public String getVersion() {
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
		};
		watcher.monitoring(metricsTarget);

	}
}