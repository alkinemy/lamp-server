package lamp.monitoring.core.notification.mms;

import lamp.monitoring.core.notification.sms.SmsNotificationAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MmsNotificationAction extends SmsNotificationAction {

	private String type = "mms";

	private String subject;

}
