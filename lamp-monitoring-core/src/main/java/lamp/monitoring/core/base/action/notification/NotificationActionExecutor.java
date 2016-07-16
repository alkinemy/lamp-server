package lamp.monitoring.core.base.action.notification;

import lamp.monitoring.core.base.alert.AlertActionExecutor;
import lamp.monitoring.core.base.alert.model.Alert;
import lamp.monitoring.core.base.alert.model.AlertActionContext;
import lamp.monitoring.core.base.alert.model.AlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.WeakHashMap;

@Slf4j
public abstract class NotificationActionExecutor<A extends NotificationAction> implements AlertActionExecutor<A> {

	private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

	private Map<String, Long> lastNotificationTimeMap = new WeakHashMap<>();

	@Override public void execute(A action, AlertActionContext context) {
		Alert alert = context.getAlert();

		Long lastNotificationTime = lastNotificationTimeMap.get(alert.getId());
		long currentTime = System.currentTimeMillis();

		if (lastNotificationTime == null || (lastNotificationTime + (action.getNotificationIntervalSeconds() * 1000)) <= currentTime) {
			lastNotificationTimeMap.put(alert.getId(), currentTime);

			doExecute(action, context);
		}
	}

	protected abstract void doExecute(A action, AlertActionContext context);


	protected String getRenderedString(String string, AlertEvent alertEvent) {
		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(string, new TemplateParserContext("#{", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(alertEvent);
		evaluationContext.addPropertyAccessor(new MapAccessor());
		return expression.getValue(evaluationContext, String.class);
	}

}
