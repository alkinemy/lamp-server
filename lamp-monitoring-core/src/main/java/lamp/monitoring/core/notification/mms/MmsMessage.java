package lamp.monitoring.core.notification.mms;

import lamp.monitoring.core.notification.sms.SmsMessage;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MmsMessage extends SmsMessage {

	private String subject;

	public MmsMessage(String subject, String message, long currentTimeMillis) {
		super(message, currentTimeMillis);
		this.subject = subject;
	}

}
