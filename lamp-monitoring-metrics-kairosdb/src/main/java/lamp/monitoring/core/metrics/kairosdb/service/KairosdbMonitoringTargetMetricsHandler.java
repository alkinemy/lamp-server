package lamp.monitoring.core.metrics.kairosdb.service;

public class KairosdbMonitoringTargetMetricsHandler  {

//	private SpelTargetMetricsAlertRuleProvider metricsAlertRuleProvider;
//	private KairosdbClient kairosdbClient;
//	private AlertEventProducer alertEventProducer;
//
//	public KairosdbMonitoringTargetMetricsHandler(SpelTargetMetricsAlertRuleProvider metricsAlertRuleProvider,
//												  KairosdbClient kairosdbClient,
//												  AlertEventProducer alertEventProducer) {
//		this.metricsAlertRuleProvider = metricsAlertRuleProvider;
//		this.kairosdbClient = kairosdbClient;
//		this.alertEventProducer = alertEventProducer;
//	}
//
//
//	@Override
//	protected void monitoring(MetricsTarget metricsTarget) {
//		Date stateTime = new Date();
//		List<KairosdbMetricsAlertRule> alertRules = metricsAlertRuleProvider.getMetricsAlertRules(KairosdbMetricsAlertRule.class);
//		for (KairosdbMetricsAlertRule alertRule : alertRules) {
//			AlertEvent event = new AlertEvent();
//			event.setTenant(metricsTarget);
//			event.setAlertRule(alertRule);
//			event.setTimestamp(stateTime);
//
//			KairosdbAlertRuleExpression expression = alertRule.getExpression();
//			try {
//				QueryBuilder queryBuilder = expression.getQueryBuilder(MetricsTagConstants.ID, metricsTarget.getId());
//				QueryResponse response = kairosdbClient.query(queryBuilder);
//
//				if (CollectionUtils.isNotEmpty(response.getQueries())) {
//					List<Results> resultsList = response.getQueries().get(0).getResults();
//					List<DataPoint> dataPoints = CollectionUtils.isNotEmpty(resultsList)  ? resultsList.get(0).getDataPoints() : null;
//					DataPoint dataPoint = CollectionUtils.isNotEmpty(dataPoints) ? dataPoints.get(dataPoints.size() - 1) : null;
//
//					if (dataPoint != null) {
//						Map<String, Object> dimension = new LinkedHashMap<>();
//						dimension.put(String.valueOf(dataPoint.getTimestamp()), dataPoint.getValue());
//						event.setReasonData(dimension);
//					}
//
//					AlertStateCode state = expression.evaluate(dataPoint);
//					event.setState(state);
//				} else {
//					event.setState(AlertStateCode.UNDETERMINED);
//					if (CollectionUtils.isNotEmpty(response.getErrors())) {
//						event.setReason(response.getErrors().get(0));
//					} else {
//						event.setReason(response.getBody());
//					}
//				}
//			} catch (Throwable t) {
//				event.setState(AlertStateCode.UNDETERMINED);
//				event.setReason(ExceptionUtils.getStackTrace(t));
//			}
//
//			alertEventProducer.send(event);
//		}
//	}

}
