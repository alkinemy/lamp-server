package lamp.alarm.health.service;

import lamp.alarm.core.model.*;
import lamp.alarm.core.service.AlarmEventProducer;
import lamp.common.metrics.*;
import lamp.common.utils.ExceptionUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HealthWatcher implements HealthProcessor {

	private HealthAlarmDefinitionProvider healthAlarmDefinitionProvider;
	private AlarmEventProducer alarmEventProducer;

	public HealthWatcher(HealthAlarmDefinitionProvider healthAlarmDefinitionProvider, AlarmEventProducer alarmEventProducer) {
		this.healthAlarmDefinitionProvider = healthAlarmDefinitionProvider;
		this.alarmEventProducer = alarmEventProducer;
	}

	@Override
	public void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
		if (targetHealth == null) {
			targetHealth = new TargetHealth();
			targetHealth.setId(healthTarget.getId());
			targetHealth.setName(healthTarget.getName());

			Map<String, Object> health = new LinkedHashMap<>();
			health.put(HealthConstants.STATUS, HealthStatusCode.UNKNOWN.name());
			if (t != null) {
				health.put(HealthConstants.DESCRIPTION, ExceptionUtils.getStackTrace(t));
			}
			targetHealth.setHealth(health);
		}

		watch(targetHealth);
	}

	protected void watch(TargetHealth targetHealth) {
		Date stateTime = new Date();
		List<AlarmDefinition> alarmDefinitions = healthAlarmDefinitionProvider.getHealthAlarmDefinitions();
		for (AlarmDefinition alarmDefinition : alarmDefinitions) {
			AlarmExpression expression = alarmDefinition.getExpression();

			AlarmEvent alarmEvent = new AlarmEvent();
			alarmEvent.setTenantId(targetHealth.getId());
			alarmEvent.setAlarmDefinitionId(alarmDefinition.getId());
			alarmEvent.setAlarmType(alarmDefinition.getType());
			alarmEvent.setSeverity(alarmDefinition.getSeverity());
			alarmEvent.setDimension(targetHealth.getHealth());
			alarmEvent.setStateTime(stateTime);
			try {
				AlarmState state = expression.getState(targetHealth);
				alarmEvent.setState(state);
			} catch (Throwable t) {
				alarmEvent.setState(AlarmState.UNDETERMINED);
				alarmEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alarmEventProducer.send(alarmEvent);
		}
	}

}
