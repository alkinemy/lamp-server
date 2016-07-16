package lamp.monitoring.core.base.action.notification.sms;

import lamp.monitoring.core.base.action.notification.NotificationAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsNotificationAction extends NotificationAction {

	private String type = "sms";

	private String message;
	private String phoneNumbers;

}
