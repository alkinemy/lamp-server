package lamp.monitoring.metrics.kairosdb.model;

import com.google.gson.internal.LazilyParsedNumber;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.alert.model.AlertRuleExpression;
import lamp.monitoring.core.alert.model.AlertState;
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
public class KairosdbAlertRuleExpression implements AlertRuleExpression<DataPoint> {

	private int duration = 10; // sec
	private String metric;
	private String[] tagNames;

	private KairosdbFunction[] functions;

	private String threshold;

	public QueryBuilder getQueryBuilder(String tagName, String... tagValues) {
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
		if (StringUtils.isNotBlank(tagName)) {
			queryMetric.addTag(tagName, tagValues);
		}
		return queryBuilder;
	}

	@Override public AlertState evaluate(DataPoint context) {
		Object value = context.getValue();
		LazilyParsedNumber number = new LazilyParsedNumber(threshold);
		Number number1 = 1;

		System.out.println(context.getValue());
		System.out.println(context.getValue().getClass());
		return null;
	}
}
