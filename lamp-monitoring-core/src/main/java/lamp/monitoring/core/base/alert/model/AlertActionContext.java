package lamp.monitoring.core.base.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AlertActionContext {

	private Alert alert;

	private AlertEvent alertEvent;

	private List<AlertEventHistory> alertEventHistories;

}
