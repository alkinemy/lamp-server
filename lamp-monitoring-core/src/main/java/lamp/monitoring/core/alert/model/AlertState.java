package lamp.monitoring.core.alert.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlertState {

	private AlertStateCode code;
	private Object value;

}
