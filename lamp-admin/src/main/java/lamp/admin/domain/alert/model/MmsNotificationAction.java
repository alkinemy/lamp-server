package lamp.admin.domain.alert.model;

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
