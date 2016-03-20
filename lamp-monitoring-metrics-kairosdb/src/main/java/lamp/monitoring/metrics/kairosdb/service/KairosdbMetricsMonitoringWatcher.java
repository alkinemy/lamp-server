package lamp.monitoring.metrics.kairosdb.service;

import com.google.common.base.Optional;
import lamp.common.collector.model.MetricsTagConstants;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.ExceptionUtils;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.monitoring.core.alert.model.event.AlertRuleExpressionEvaluationEvent;
import lamp.monitoring.core.alert.service.AlertEventProducer;
import lamp.monitoring.core.metrics.model.MonitoringMetricsTarget;
import lamp.monitoring.core.metrics.service.MetricsAlertRuleProvider;
import lamp.monitoring.core.metrics.service.MonitoringMetricsTargetProvider;
import lamp.monitoring.metrics.kairosdb.model.KairosdbAlertRuleExpression;
import lamp.monitoring.metrics.kairosdb.model.KairosdbMetricsAlertRule;
import lamp.support.kairosdb.KairosdbClient;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Results;

import java.util.*;
import java.util.stream.Collectors;

public class KairosdbMetricsMonitoringWatcher {

	private MetricsAlertRuleProvider metricsAlertRuleProvider;
	private MonitoringMetricsTargetProvider monitoringMetricsTargetProvider;
	private KairosdbClient kairosdbClient;
	private AlertEventProducer alertEventProducer;

	public KairosdbMetricsMonitoringWatcher(MetricsAlertRuleProvider metricsAlertRuleProvider,
											MonitoringMetricsTargetProvider monitoringMetricsTargetProvider,
											KairosdbClient kairosdbClient,
											AlertEventProducer alertEventProducer) {
		this.metricsAlertRuleProvider = metricsAlertRuleProvider;
		this.monitoringMetricsTargetProvider = monitoringMetricsTargetProvider;
		this.kairosdbClient = kairosdbClient;
		this.alertEventProducer = alertEventProducer;
	}

	public void watch() throws Exception {
		Date stateTime = new Date();
		List<KairosdbMetricsAlertRule> alertRules = metricsAlertRuleProvider.getMetricsAlertRules(KairosdbMetricsAlertRule.class);
		for (KairosdbMetricsAlertRule alertRule : alertRules) {
			KairosdbAlertRuleExpression expression = alertRule.getExpression();
			QueryBuilder queryBuilder = expression.getQueryBuilder(null);
			QueryResponse response = kairosdbClient.query(queryBuilder);

			if (CollectionUtils.isNotEmpty(response.getQueries())) {
				List<Results> resultsList = Optional.fromNullable(response.getQueries().get(0).getResults()).or(Collections.emptyList());

				Map<String, DataPoint> dataPointMap =
						resultsList.stream()
								.filter(this::hasTenantId)
								.collect(Collectors.toMap(this::getTenantId, rs -> rs.getDataPoints().stream().reduce((f, s) -> s).get()));

				monitoringMetricsTargetProvider.getMonitoringMetricsTargets()
						.forEach(metricsTarget -> expressionEvaluate(stateTime, alertRule, metricsTarget, dataPointMap.get(metricsTarget.getId())));
			}
		}
	}

	private boolean hasTenantId(Results rs) {
		return StringUtils.isNotEmpty(getTenantId(rs));
	}


	protected String getTenantId(Results rs) {
		return rs.getTags().get(MetricsTagConstants.ID).get(0);
	}

	protected void expressionEvaluate(Date stateTime,
									  KairosdbMetricsAlertRule alertRule,
									  MonitoringMetricsTarget metricsTarget,
									  DataPoint dataPoint) {
		AlertRuleExpressionEvaluationEvent event = new AlertRuleExpressionEvaluationEvent();
		event.setTenant(metricsTarget);
		event.setAlertRule(alertRule);
		event.setTimestamp(stateTime);

		try {
			if (dataPoint != null) {
				Map<String, Object> dimension = new LinkedHashMap<>();
				dimension.put(String.valueOf(dataPoint.getTimestamp()), dataPoint.getValue());
				event.setReasonData(dimension);
			}

			KairosdbAlertRuleExpression expression = alertRule.getExpression();
			AlertState state = expression.evaluate(dataPoint);
			event.setState(state);
		} catch (Throwable t) {
			event.setState(AlertState.UNDETERMINED);
			event.setReason(ExceptionUtils.getStackTrace(t));
		}

		alertEventProducer.send(event);

	}
}
