package lamp.alarm.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
public class Alarm {

    private String tenantId;
    private String alarmDefinitionId;
    private Map<String, Object> dimension;
    private AlarmState state;
    private Date stateTime;

}
