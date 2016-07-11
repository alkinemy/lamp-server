package lamp.admin.domain.alert;

import lamp.admin.domain.alert.model.MmsNotificationAction;
import lamp.monitoring.core.alert.model.AlertActionContext;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.notification.mms.MmsHttpSender;
import lamp.monitoring.core.notification.mms.MmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MmsNotificationActionExecutor extends AbstractSmsNotificationActionExecutor<MmsMessage, MmsNotificationAction> {

	@Autowired(required = false)
	public MmsNotificationActionExecutor(
		MmsHttpSender mmsHttpSender) {
		super(mmsHttpSender);
	}

	@Override
	protected MmsMessage newMessage(MmsNotificationAction action, AlertActionContext context) {
		AlertEvent alertEvent = context.getAlertEvent();
		String subject = getRenderedString(action.getSubject(), alertEvent);
		String message = getRenderedString(action.getMessage(), alertEvent);
		return new MmsMessage(subject, message, System.currentTimeMillis());
	}

	@Override protected MmsMessage newMessageWithPhoneNumber(MmsMessage message, String mobilePhoneNumber) {
		MmsMessage sendMessage = new MmsMessage();
		BeanUtils.copyProperties(message, sendMessage);
		sendMessage.setPhoneNumber(mobilePhoneNumber);
		return sendMessage;
	}

}
