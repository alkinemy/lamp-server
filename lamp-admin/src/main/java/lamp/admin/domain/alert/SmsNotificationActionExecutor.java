package lamp.admin.domain.alert;

import lamp.admin.domain.alert.model.SmsNotificationAction;
import lamp.monitoring.core.alert.model.AlertActionContext;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.notification.mms.SmsHttpSender;
import lamp.monitoring.core.notification.mms.SmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsNotificationActionExecutor extends AbstractSmsNotificationActionExecutor<SmsMessage, SmsNotificationAction> {

	@Autowired(required = false)
	public SmsNotificationActionExecutor(SmsHttpSender smsHttpSender) {
		super(smsHttpSender);
	}

	@Override protected SmsMessage newMessageWithPhoneNumber(SmsMessage message, String mobilePhoneNumber) {
		SmsMessage sendMessage = new SmsMessage();
		BeanUtils.copyProperties(message, sendMessage);
		sendMessage.setPhoneNumber(mobilePhoneNumber);
		return sendMessage;
	}

	protected SmsMessage newMessage(SmsNotificationAction action, AlertActionContext context) {
		AlertEvent alertEvent = context.getAlertEvent();
		String message = getRenderedString(action.getMessage(), alertEvent);
		return new SmsMessage(message, System.currentTimeMillis());
	}

}
