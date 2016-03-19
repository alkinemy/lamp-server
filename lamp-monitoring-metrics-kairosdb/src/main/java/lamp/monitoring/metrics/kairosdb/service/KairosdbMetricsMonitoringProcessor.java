package lamp.monitoring.metrics.kairosdb.service;

import lamp.common.collector.model.MetricsTagConstants;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.ExceptionUtils;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.monitoring.core.alert.model.event.AlertRuleExpressionEvaluationEvent;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.monitoring.core.metrics.service.MetricsAlertRuleProvider;
import lamp.monitoring.core.metrics.service.MetricsMonitoringProcessor;
import lamp.monitoring.metrics.kairosdb.model.KairosdbAlertRuleExpression;
import lamp.monitoring.metrics.kairosdb.model.KairosdbMetricsAlertRule;
import lamp.support.kairosdb.KairosdbClient;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Results;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KairosdbMetricsMonitoringProcessor extends MetricsMonitoringProcessor {

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
	protected void monitoring(MetricsTarget metricsTarget) {
		Date stateTime = new Date();
		List<KairosdbMetricsAlertRule> alertRules = metricsAlertRuleProvider.getMetricsAlertRules(KairosdbMetricsAlertRule.class);
		for (KairosdbMetricsAlertRule alertRule : alertRules) {
			AlertRuleExpressionEvaluationEvent event = new AlertRuleExpressionEvaluationEvent();
			event.setTenantId(metricsTarget.getId());
			event.setAlertRuleId(alertRule.getId());
			event.setAlertType(alertRule.getType());
			event.setSeverity(alertRule.getSeverity());
			event.setStateTime(stateTime);

			KairosdbAlertRuleExpression expression = alertRule.getExpression();
			try {
				QueryBuilder queryBuilder = expression.getQueryBuilder(MetricsTagConstants.ID, metricsTarget.getId());
				QueryResponse response = kairosdbClient.query(queryBuilder);

				if (CollectionUtils.isNotEmpty(response.getQueries())) {
					List<Results> resultsList = response.getQueries().get(0).getResults();
					List<DataPoint> dataPoints = CollectionUtils.isNotEmpty(resultsList)  ? resultsList.get(0).getDataPoints() : null;
					DataPoint dataPoint = CollectionUtils.isNotEmpty(dataPoints) ? dataPoints.get(dataPoints.size() - 1) : null;

					if (dataPoint != null) {
						Map<String, Object> dimension = new LinkedHashMap<>();
						dimension.put(String.valueOf(dataPoint.getTimestamp()), dataPoint.getValue());
						event.setDimension(dimension);
					}

					AlertState state = expression.evaluate(dataPoint);
					event.setState(state);
				} else {
					event.setState(AlertState.UNDETERMINED);
					if (CollectionUtils.isNotEmpty(response.getErrors())) {
						event.setStateDescription(response.getErrors().get(0));
					} else {
						event.setStateDescription(response.getBody());
					}
				}
			} catch (Throwable t) {
				event.setState(AlertState.UNDETERMINED);
				event.setStateDescription(ExceptionUtils.getStackTrace(t));
			}

			alertEventProducer.send(event);
		}
	}

}
