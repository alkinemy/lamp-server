package lamp.monitoring.core.metrics.model;

import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.common.utils.StringUtils;
import lamp.monitoring.core.alert.model.AbstractAlertRule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Getter
@Setter
public class TargetMetricsAlertRule extends AbstractAlertRule<MonitoringTargetMetrics, TargetMetricsAlertRuleExpression> {

	private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

	private String targetExpression;

	@Override public boolean isAlertTarget(MonitoringTargetMetrics target) {
		if (StringUtils.isBlank(targetExpression)) {
			return true;
		}

		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(targetExpression, new TemplateParserContext("${", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(target);
		return expression.getValue(evaluationContext, Boolean.class);
	}


}
