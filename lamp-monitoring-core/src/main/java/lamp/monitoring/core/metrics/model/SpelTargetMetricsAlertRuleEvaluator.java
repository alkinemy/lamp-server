package lamp.monitoring.core.metrics.model;

import lamp.common.collector.model.TargetMetrics;
import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.alert.model.AlertRuleEvaluator;
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
public class SpelTargetMetricsAlertRuleEvaluator implements AlertRuleEvaluator<SpelTargetMetricsAlertRule, MonitoringTargetMetrics> {

	private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

	@Override public boolean isAlertTarget(SpelTargetMetricsAlertRule rule, MonitoringTargetMetrics target) {
		if (!rule.isEnabled()) {
			return false;
		}

		String targetExpression = rule.getTargetExpression();
		if (StringUtils.isBlank(targetExpression)) {
			return true;
		}

		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(targetExpression, new TemplateParserContext("${", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(target);
		return expression.getValue(evaluationContext, Boolean.class);
	}

	@Override public AlertState evaluate(SpelTargetMetricsAlertRule rule, MonitoringTargetMetrics target) {
		String ruleExpression = rule.getRuleExpression();
		String valueExpression = rule.getValueExpression();
		try {
			boolean isAlert = isAlert(ruleExpression, target);
			String value = getValue(valueExpression, target);
			return isAlert ? new AlertState(AlertStateCode.ALERT, value) : new AlertState(AlertStateCode.OK, value);
		} catch (Exception e) {
			log.warn("SpelTargetMetricsAlertRuleExpression evaluate failed", e);
			return new AlertState(AlertStateCode.UNDETERMINED, AlertStateCode.UNDETERMINED.name(), e.getMessage());
		}
	}

	protected boolean isAlert(String ruleExpression, TargetMetrics context) {
		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(ruleExpression, new TemplateParserContext("#{", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
		evaluationContext.addPropertyAccessor(new MapAccessor());
		return expression.getValue(evaluationContext, Boolean.class);
	}

	protected String getValue(String valueExpression, TargetMetrics context) {
		if (StringUtils.isBlank(valueExpression)) {
			return "undefined";
		}
		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(valueExpression, new TemplateParserContext("#{", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
		evaluationContext.addPropertyAccessor(new MapAccessor());
		return expression.getValue(evaluationContext, String.class);
	}

}
