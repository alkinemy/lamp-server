package lamp.monitoring.core.base.action.notification;

import lamp.monitoring.core.base.alert.model.AlertAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class NotificationAction extends AlertAction {

	private long notificationIntervalSeconds = 60;

}
