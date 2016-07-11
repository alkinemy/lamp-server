package lamp.monitoring.core.notification.sms;

import lamp.monitoring.core.notification.NotificationMessage;
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
