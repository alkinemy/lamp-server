package lamp.monitoring.core.base.action.notification.sms;

import lamp.monitoring.core.base.action.notification.NotificationMessage;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessage implements NotificationMessage {

	private String phoneNumber;
	private String message;

	private long currentTimeMillis;

	public SmsMessage(String message, long currentTimeMillis) {
		this.message = message;
		this.currentTimeMillis = currentTimeMillis;
	}

}
