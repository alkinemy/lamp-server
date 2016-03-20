package lamp.monitoring.core.alert.model.event;

import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertCreateEvent implements AlertEvent {

    private Alert alert;

}
