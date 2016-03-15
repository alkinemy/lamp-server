package lamp.alarm.metrics.kairosdb.model;

import com.google.gson.internal.LazilyParsedNumber;
import lamp.alarm.core.model.AlarmExpression;
import lamp.alarm.core.model.AlarmState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.QueryMetric;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.builder.aggregator.RateAggregator;
import org.kairosdb.client.builder.grouper.TagGrouper;

@Getter
@Setter
@ToString
public class KairosdbAlarmExpression implements AlarmExpression<DataPoint> {

	private int duration = 10;
	private String metric;
	private String[] tagNames;

	private KairosdbFunction[] functions;

	private String threshold;

	public QueryBuilder getQueryBuilder(String tagName, String tagValue) {
		QueryBuilder queryBuilder = QueryBuilder.getInstance();

		queryBuilder
			.setStart(getDuration(), TimeUnit.SECONDS);
		QueryMetric queryMetric = queryBuilder
			.addMetric(getMetric());

		queryMetric.addGrouper(new TagGrouper(getTagNames()));
		if (getFunctions() != null) {
			for (KairosdbFunction function : getFunctions()) {
				if (KairosdbFunction.RATE_SECOND.equals(function)) {
					queryMetric.addAggregator(new RateAggregator(TimeUnit.SECONDS));
				}
			}
		}
		return queryBuilder;
	}

	@Override public AlarmState getState(DataPoint dataPoint) {
		Object value = dataPoint.getValue();
		LazilyParsedNumber number = new LazilyParsedNumber(threshold);
		System.out.println(dataPoint.getValue());
		System.out.println(dataPoint.getValue().getClass());
		return null;
	}
}
