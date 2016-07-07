package lamp.admin.domain.notification;

import lamp.monitoring.core.alert.AlertActionExecutor;
import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertActionContext;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.notify.mms.MmsHttpNotifier;
import lamp.monitoring.core.notify.mms.MmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

@Slf4j
@Component
public class MmsNotificationActionExecutor implements AlertActionExecutor<MmsNotificationAction> {

	private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

	@Autowired(required = false)
	private MmsHttpNotifier mmsHttpNotifier;

	private Map<String, Long> lastNotificationTimeMap = new WeakHashMap<>();

	@Override public void execute(AlertActionContext context, MmsNotificationAction action) {
		Alert alert = context.getAlert();
		AlertEvent alertEvent = context.getAlertEvent();

		Long lastNotificationTime = lastNotificationTimeMap.get(alert.getId());
		long currentTime = System.currentTimeMillis();

		if (lastNotificationTime == null || (lastNotificationTime + (action.getNotificationIntervalSeconds() * 1000)) <= currentTime) {
			lastNotificationTimeMap.put(alert.getId(), currentTime);

			String phoneNumber = "000-0000-0000";
			String subject = getRenderedString(action.getSubject(), alertEvent);
			String message = getRenderedString(action.getMessage(), alertEvent);

			try {
				MmsMessage mmsMessage = new MmsMessage(phoneNumber, subject, message);
				mmsHttpNotifier.send(mmsMessage);
			} catch (Exception e) {
				log.warn("MmsNotificationActionExecutor execute failed", e);
			}
		}
	}

	protected String getRenderedString(String string, AlertEvent alertEvent) {
		Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(string, new TemplateParserContext("#{", "}"));
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(alertEvent);
		evaluationContext.addPropertyAccessor(new MapAccessor());
		return expression.getValue(evaluationContext, String.class);
	}

}
