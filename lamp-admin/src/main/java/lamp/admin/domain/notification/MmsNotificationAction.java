package lamp.admin.domain.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MmsNotificationAction extends NotificationAction {

	private String subject;
	private String message;

	private String phoneNumbers;
	private String tenantIds;



}
