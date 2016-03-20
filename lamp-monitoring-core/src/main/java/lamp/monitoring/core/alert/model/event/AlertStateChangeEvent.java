package lamp.monitoring.core.alert.model.event;

import lamp.monitoring.core.alert.model.Alert;
import lamp.monitoring.core.alert.model.AlertEvent;
import lamp.monitoring.core.alert.model.AlertStateHistory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertStateChangeEvent implements AlertEvent {

    private Alert alert;

}
