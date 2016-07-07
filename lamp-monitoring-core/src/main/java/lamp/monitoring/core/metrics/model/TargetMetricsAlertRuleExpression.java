package lamp.monitoring.core.metrics.model;

import lamp.common.collector.model.TargetMetrics;
import lamp.monitoring.core.alert.model.AlertRuleExpression;
import lamp.monitoring.core.alert.model.AlertState;
import lamp.monitoring.core.alert.model.AlertStateCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Slf4j
@Getter
@Setter
@ToString
public class TargetMetricsAlertRuleExpression implements AlertRuleExpression<TargetMetrics> {

	private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();
	private String ruleExpression;
	private String valueExpression;

	@Override public AlertState evaluate(TargetMetrics context) {
		try {
			boolean isAlert = isAlert(context);
			String value = getValue(context);
			return isAlert ? new AlertState(AlertStateCode.ALERT, value) : new AlertState(AlertStateCode.OK, value);
		} catch (Exception e) {
			log.error("HostAlertRule evaluate failed", e);
			return new AlertState(AlertStateCode.UNDETERMINED, AlertStateCode.UNDETERMINED.name());
		}
	}

	protected boolean isAlert(TargetMetrics context) {
		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(ruleExpression, new TemplateParserContext("#{", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
		evaluationContext.addPropertyAccessor(new MapAccessor());
		return expression.getValue(evaluationContext, Boolean.class);
	}

	protected String getValue(TargetMetrics context) {
		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(valueExpression, new TemplateParserContext("#{", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
		evaluationContext.addPropertyAccessor(new MapAccessor());
		return expression.getValue(evaluationContext, String.class);
	}


}
