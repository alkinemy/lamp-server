package lamp.admin.domain.alert.model;

import lamp.monitoring.core.alert.model.AlertAction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class NotificationAction implements AlertAction {

	private long notificationIntervalSeconds = 60;

}
