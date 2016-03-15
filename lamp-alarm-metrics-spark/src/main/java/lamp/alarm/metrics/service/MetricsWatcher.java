package lamp.alarm.metrics.service;

import lamp.alarm.core.model.AlarmDefinition;
import lamp.alarm.core.model.AlarmEvent;
import lamp.alarm.core.model.AlarmExpression;
import lamp.alarm.core.model.AlarmState;
import lamp.alarm.core.service.AlarmEventProducer;
import lamp.common.metrics.MetricsProcessor;
import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;
import lamp.common.utils.ExceptionUtils;

import java.util.Date;
import java.util.List;

public class MetricsWatcher implements MetricsProcessor {

	private MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider;
	private AlarmEventProducer alarmEventProducer;

	public MetricsWatcher(MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider, AlarmEventProducer alarmEventProducer) {
		this.metricsAlarmDefinitionProvider = metricsAlarmDefinitionProvider;
		this.alarmEventProducer = alarmEventProducer;
	}

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		if (targetMetrics == null) {
			targetMetrics = new TargetMetrics();
			targetMetrics.setId(metricsTarget.getId());
			targetMetrics.setName(metricsTarget.getName());
		}

		watch(targetMetrics);
	}

	protected void watch(TargetMetrics targetMetrics) {
		Date stateTime = new Date();
		List<AlarmDefinition> alarmDefinitions = metricsAlarmDefinitionProvider.getMetricsAlarmDefinitions();
		for (AlarmDefinition alarmDefinition : alarmDefinitions) {
			AlarmEvent alarmEvent = new AlarmEvent();
			alarmEvent.setTenantId(targetMetrics.getId());
			alarmEvent.setAlarmDefinitionId(alarmDefinition.getId());
			alarmEvent.setAlarmType(alarmDefinition.getType());
			alarmEvent.setSeverity(alarmDefinition.getSeverity());
			alarmEvent.setDimension(targetMetrics.getMetrics());
			alarmEvent.setStateTime(stateTime);

			AlarmExpression expression = alarmDefinition.getExpression();
			try {
				AlarmState state = expression.getState(targetMetrics);
				alarmEvent.setState(state);
			} catch (Throwable t) {
				alarmEvent.setState(AlarmState.UNDETERMINED);
				alarmEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alarmEventProducer.send(alarmEvent);
		}
	}

}
