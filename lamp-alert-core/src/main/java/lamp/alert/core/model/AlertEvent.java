package lamp.alert.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class AlertEvent {

	private String alarmType;

	private String tenantId;
	private String alarmDefinitionId;
	private AlertState state;
	private String stateDescription;
	private Date stateTime;
	private AlertSeverity severity;

	private Map<String, Object> dimension;


}
