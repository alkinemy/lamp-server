package lamp.alarm.metrics.kairosdb.service;

import com.google.common.collect.Lists;
import lamp.alarm.core.model.AlarmDefinition;
import lamp.alarm.core.model.AlarmEvent;
import lamp.alarm.core.service.AlarmEventProducer;
import lamp.alarm.metrics.kairosdb.model.KairosdbAlarmExpression;
import lamp.alarm.metrics.kairosdb.model.MetricsAlarmDefinition;
import lamp.alarm.metrics.service.MetricsAlarmDefinitionProvider;
import lamp.alarm.metrics.service.MetricsKairosdbWatcher;
import lamp.common.metrics.MetricsTarget;
import lamp.support.kairosdb.KairosdbClient;
import lamp.support.kairosdb.KairosdbProperties;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by kangwoo on 2016. 3. 15..
 */
public class MetricsKairosdbWatcherTest {

	@Test
	public void testWatch() throws Exception {
		MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider = new MetricsAlarmDefinitionProvider() {
			@Override public List<AlarmDefinition> getMetricsAlarmDefinitions() {
				MetricsAlarmDefinition metricsAlarmDefinition = new MetricsAlarmDefinition();
				KairosdbAlarmExpression kairosdbAlarmExpression = new KairosdbAlarmExpression();
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
		AlarmEventProducer alarmEventProducer = new AlarmEventProducer() {
			@Override public void send(AlarmEvent alarmEvent) {
				System.out.println("alarmEvent = " + alarmEvent);
			}
		};
		MetricsKairosdbWatcher watcher = new MetricsKairosdbWatcher(metricsAlarmDefinitionProvider, kairosdbClient, alarmEventProducer);

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
		watcher.watch(metricsTarget);

	}
}