package lamp.alarm.metrics.kairosdb.service;

import lamp.alarm.core.model.AlarmDefinition;
import lamp.alarm.core.model.AlarmExpression;
import lamp.alarm.core.service.AlarmEventProducer;
import lamp.alarm.metrics.kairosdb.model.KairosdbAlarmExpression;
import lamp.alarm.metrics.kairosdb.model.KairosdbFunction;
import lamp.alarm.metrics.service.MetricsAlarmDefinitionProvider;
import lamp.common.metrics.MetricsTagConstants;
import lamp.common.utils.CollectionUtils;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.QueryMetric;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.builder.aggregator.RateAggregator;
import org.kairosdb.client.builder.grouper.TagGrouper;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Results;

import java.util.Date;
import java.util.List;

public class MetricsKairosdbWatcher2 {

	private MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider;
	private AlarmEventProducer alarmEventProducer;

	public MetricsKairosdbWatcher2(MetricsAlarmDefinitionProvider metricsAlarmDefinitionProvider, AlarmEventProducer alarmEventProducer) {
		this.metricsAlarmDefinitionProvider = metricsAlarmDefinitionProvider;
		this.alarmEventProducer = alarmEventProducer;
	}

	public void watch() throws Exception {
		Date stateTime = new Date();
		List<AlarmDefinition> alarmDefinitions = metricsAlarmDefinitionProvider.getMetricsAlarmDefinitions();
		for (AlarmDefinition alarmDefinition : alarmDefinitions) {
			AlarmExpression expression = alarmDefinition.getExpression();
			if (expression instanceof KairosdbAlarmExpression) {

				KairosdbAlarmExpression expression1 = (KairosdbAlarmExpression) expression;
				HttpClient client = new HttpClient("http://10.255.10.218:8080");
				QueryBuilder queryBuilder = QueryBuilder.getInstance();

				queryBuilder
					.setStart(expression1.getDuration(), TimeUnit.SECONDS);
					//				.setEnd(1,)
				QueryMetric queryMetric = queryBuilder.addMetric(expression1.getMetric());
				queryMetric.addGrouper(new TagGrouper(expression1.getTagNames()));
				if (expression1.getFunctions() != null) {
					for (KairosdbFunction function : expression1.getFunctions()) {
						if (KairosdbFunction.RATE_SECOND.equals(function)) {
							queryMetric.addAggregator(new RateAggregator(TimeUnit.SECONDS));
						}
					}
				}


				QueryResponse response = client.query(queryBuilder);
				if (CollectionUtils.isNotEmpty(response.getQueries())) {
					List<Results> resultsList = response.getQueries().get(0).getResults();
					for (Results results : resultsList) {
						System.out.println(results.getName());
						System.out.println(results.getTags().get(MetricsTagConstants.ID));
						//					System.out.println(results.getTags().get(MetricsTagConstants.ID));

						System.out.println(results.getDataPoints().get(0));
						System.out.println(results.getGroupResults().get(0).getName());
					}

				}
				System.out.println(response.getBody());
				System.out.println(response.getErrors());
				System.out.println(response.getQueries().get(0).getResults());
				System.out.println(response.getStatusCode());




			}

//			AlarmEvent alarmEvent = new AlarmEvent();
//			alarmEvent.setTenantId(targetMetrics.getId());
//			alarmEvent.setAlarmDefinitionId(alarmDefinition.getId());
//			alarmEvent.setAlarmType(alarmDefinition.getType());
//			alarmEvent.setSeverity(alarmDefinition.getSeverity());
//			alarmEvent.setDimension(targetMetrics.getMetrics());
//			alarmEvent.setStateTime(stateTime);
//
//
//			try {
//				AlarmState state = expression.getState(targetMetrics);
//				alarmEvent.setState(state);
//			} catch (Throwable t) {
//				alarmEvent.setState(AlarmState.UNDETERMINED);
//				alarmEvent.setStateDescription(ExceptionUtils.getStackTrace(t));
//			}
//
//			alarmEventProducer.send(alarmEvent);
		}
	}

}
