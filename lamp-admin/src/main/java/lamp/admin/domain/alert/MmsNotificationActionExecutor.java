package lamp.admin.domain.alert;

import lamp.admin.domain.alert.model.MmsNotificationAction;
import lamp.common.monitoring.model.Tenant;
import lamp.common.monitoring.model.TenantUser;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.StringUtils;
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

import java.util.*;
import java.util.stream.Collectors;

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

			List<String> mobilePhoneNumbers = new ArrayList<>();

			String subject = getRenderedString(action.getSubject(), alertEvent);
			String message = getRenderedString(action.getMessage(), alertEvent);

			Tenant tenant = alertEvent.getTenant();
			if (tenant != null) {
				Optional<List<TenantUser>> usersOptional = Optional.ofNullable(tenant.getUsers());
				mobilePhoneNumbers.addAll(
					usersOptional.orElse(Collections.emptyList()).stream()
						.map(TenantUser::getMobilePhoneNumber)
						.filter(StringUtils::isNotBlank)
						.collect(Collectors.toList())
				);
			}

			if (StringUtils.isNotBlank(action.getPhoneNumbers())) {
				mobilePhoneNumbers.addAll(
					Arrays.stream(StringUtils.split(action.getPhoneNumbers(), ","))
						.map(StringUtils::trim)
						.filter(StringUtils::isNotBlank)
						.collect(Collectors.toList())
				);
			}

			for (String mobilePhoneNumber : mobilePhoneNumbers) {
				try {
					MmsMessage mmsMessage = new MmsMessage(mobilePhoneNumber, subject, message);
					mmsHttpNotifier.send(mmsMessage);
				} catch (Exception e) {
					log.warn("Mms Notification send failed", e);
				}
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
