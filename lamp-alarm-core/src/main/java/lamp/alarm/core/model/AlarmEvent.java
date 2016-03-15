package lamp.alarm.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class AlarmEvent {

	private String alarmType;

	private String tenantId;
	private String alarmDefinitionId;
	private AlarmState state;
	private String stateDescription;
	private Date stateTime;
	private AlarmSeverity severity;

	private Map<String, Object> dimension;


}
