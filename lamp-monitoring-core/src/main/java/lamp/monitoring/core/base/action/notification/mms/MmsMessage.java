package lamp.monitoring.core.base.action.notification.mms;

import lamp.monitoring.core.base.action.notification.sms.SmsMessage;
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
