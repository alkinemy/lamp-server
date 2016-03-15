package lamp.alarm.metrics.service;

import lamp.alarm.core.model.AlarmDefinition;
import lamp.alarm.core.model.AlarmEvent;
import lamp.alarm.core.model.AlarmExpression;
import lamp.alarm.core.model.AlarmState;
import lamp.alarm.core.service.AlarmEventProducer;
import lamp.alarm.metrics.kairosdb.model.KairosdbAlarmExpression;
import lamp.common.metrics.MetricsProcessor;
import lamp.common.metrics.MetricsTagConstants;
import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.ExceptionUtils;
import lamp.support.kairosdb.KairosdbClient;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Results;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MetricsKairosdbWatcher implements MetricsProcessor {

	private MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider;
	private KairosdbClient kairosdbClient;
	private AlarmEventProducer alarmEventProducer;

	public MetricsKairosdbWatcher(MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider,
								  KairosdbClient kairosdbClient,
								  AlarmEventProducer alarmEventProducer) {
		this.metricsAlarmDefinitionProvider = metricsAlarmDefinitionProvider;
		this.kairosdbClient = kairosdbClient;
		this.alarmEventProducer = alarmEventProducer;
	}

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		watch(metricsTarget);
	}

	public void watch(MetricsTarget metricsTarget) {
		Date stateTime = new Date();
		List<AlarmDefinition> alarmDefinitions = metricsAlarmDefinitionProvider.getMetricsAlarmDefinitions();
		for (AlarmDefinition alarmDefinition : alarmDefinitions) {
			AlarmEvent alarmEvent = new AlarmEvent();
			alarmEvent.setTenantId(metricsTarget.getId());
			alarmEvent.setAlarmDefinitionId(alarmDefinition.getId());
			alarmEvent.setAlarmType(alarmDefinition.getType());
			alarmEvent.setSeverity(alarmDefinition.getSeverity());
			alarmEvent.setStateTime(stateTime);

			AlarmExpression expression = alarmDefinition.getExpression();
			try {
				if (expression instanceof KairosdbAlarmExpression) {
					QueryBuilder queryBuilder = ((KairosdbAlarmExpression) expression).getQueryBuilder(MetricsTagConstants.ID, metricsTarget.getId());
					QueryResponse response = kairosdbClient.query(queryBuilder);

					if (CollectionUtils.isNotEmpty(response.getQueries())) {
						List<Results> resultsList = response.getQueries().get(0).getResults();
						List<DataPoint> dataPoints = CollectionUtils.isNotEmpty(resultsList)  ? resultsList.get(0).getDataPoints() : null;
						DataPoint dataPoint = CollectionUtils.isNotEmpty(dataPoints) ? dataPoints.get(dataPoints.size() - 1) : null;

						if (dataPoint != null) {
							Map<String, Object> dimension = new LinkedHashMap<>();
							dimension.put(String.valueOf(dataPoint.getTimestamp()), dataPoint.getValue());
							alarmEvent.setDimension(dimension);
						}

						AlarmState state = expression.getState(dataPoint);
						alarmEvent.setState(state);
					} else {
						alarmEvent.setState(AlarmState.UNDETERMINED);
						if (CollectionUtils.isNotEmpty(response.getErrors())) {
							alarmEvent.setStateDescription(response.getErrors().get(0));
						} else {
							alarmEvent.setStateDescription(response.getBody());
						}
					}
				}
			} catch (Throwable t) {
				alarmEvent.setState(AlarmState.UNDETERMINED);
				alarmEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alarmEventProducer.send(alarmEvent);
		}
	}

}
