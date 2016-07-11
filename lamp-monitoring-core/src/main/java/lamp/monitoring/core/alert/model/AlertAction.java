package lamp.monitoring.core.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AlertAction {

	private String id;
	private String name;
	private String description;

	private String type;

}
