package lamp.alert.metrics.kairosdb.service;

import lamp.alert.core.model.AlertRule;
import lamp.alert.core.model.AlertEvent;
import lamp.alert.core.model.AlertRuleExpression;
import lamp.alert.core.model.AlertState;
import lamp.alert.core.service.AlertEventProducer;
import lamp.alert.metrics.kairosdb.model.KairosdbAlertRuleExpression;
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

public class KairosdbMetricsMonitoringProcessor implements MetricsProcessor {

	private MetricsAlertRuleProvider metricsAlertRuleProvider;
	private KairosdbClient kairosdbClient;
	private AlertEventProducer alertEventProducer;

	public KairosdbMetricsMonitoringProcessor(MetricsAlertRuleProvider metricsAlertRuleProvider,
											  KairosdbClient kairosdbClient,
											  AlertEventProducer alertEventProducer) {
		this.metricsAlertRuleProvider = metricsAlertRuleProvider;
		this.kairosdbClient = kairosdbClient;
		this.alertEventProducer = alertEventProducer;
	}

	@Override
	public void process(MetricsTarget metricsTarget, TargetMetrics targetMetrics, Throwable t) {
		watch(metricsTarget);
	}

	public void watch(MetricsTarget metricsTarget) {
		Date stateTime = new Date();
		List<AlertRule> alertRules = metricsAlertRuleProvider.getMetricsAlarmDefinitions();
		for (AlertRule alertRule : alertRules) {
			AlertEvent alertEvent = new AlertEvent();
			alertEvent.setTenantId(metricsTarget.getId());
			alertEvent.setAlarmDefinitionId(alertRule.getId());
			alertEvent.setAlarmType(alertRule.getType());
			alertEvent.setSeverity(alertRule.getSeverity());
			alertEvent.setStateTime(stateTime);

			AlertRuleExpression expression = alertRule.getExpression();
			try {
				if (expression instanceof KairosdbAlertRuleExpression) {
					QueryBuilder queryBuilder = ((KairosdbAlertRuleExpression) expression).getQueryBuilder(MetricsTagConstants.ID, metricsTarget.getId());
					QueryResponse response = kairosdbClient.query(queryBuilder);

					if (CollectionUtils.isNotEmpty(response.getQueries())) {
						List<Results> resultsList = response.getQueries().get(0).getResults();
						List<DataPoint> dataPoints = CollectionUtils.isNotEmpty(resultsList)  ? resultsList.get(0).getDataPoints() : null;
						DataPoint dataPoint = CollectionUtils.isNotEmpty(dataPoints) ? dataPoints.get(dataPoints.size() - 1) : null;

						if (dataPoint != null) {
							Map<String, Object> dimension = new LinkedHashMap<>();
							dimension.put(String.valueOf(dataPoint.getTimestamp()), dataPoint.getValue());
							alertEvent.setDimension(dimension);
						}

						AlertState state = expression.evaluate(dataPoint);
						alertEvent.setState(state);
					} else {
						alertEvent.setState(AlertState.UNDETERMINED);
						if (CollectionUtils.isNotEmpty(response.getErrors())) {
							alertEvent.setStateDescription(response.getErrors().get(0));
						} else {
							alertEvent.setStateDescription(response.getBody());
						}
					}
				}
			} catch (Throwable t) {
				alertEvent.setState(AlertState.UNDETERMINED);
				alertEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alertEventProducer.send(alertEvent);
		}
	}

}
