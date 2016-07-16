package lamp.monitoring.core.base.alert.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Alert {

    private String id;

    private String ruleId;
    private String targetId;

    private AlertCondition condition;

    private long firstEventTime;

    private long lastEventTime;

}
