package lamp.monitoring.core.notification.sms;

import lamp.monitoring.core.alert.model.AlertActionContext;
import lamp.monitoring.core.alert.model.AlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class SmsNotificationActionExecutor extends AbstractSmsNotificationActionExecutor<SmsMessage, SmsNotificationAction> {

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
