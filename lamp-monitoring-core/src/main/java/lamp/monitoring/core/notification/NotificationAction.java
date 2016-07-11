package lamp.monitoring.core.notification;

import lamp.monitoring.core.alert.model.AlertAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class NotificationAction extends AlertAction {

	private long notificationIntervalSeconds = 60;

}
