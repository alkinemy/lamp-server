package lamp.monitoring.core.base.alert.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlertState {

	private AlertStateCode code;
	private String value;

	private String errorMessage;

	public AlertState(AlertStateCode code, String value) {
		this.code = code;
		this.value = value;
	}
}
