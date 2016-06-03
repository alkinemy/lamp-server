package lamp.admin.domain.monitoring.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class HealthStatus {

	@Enumerated(EnumType.STRING)
	@Column(name = "health_status")
	private HealthStatusCode code;

	@Column(name = "health_description")
	private String description;

	public static HealthStatus of(String code, String description) {
		return of(HealthStatusCode.valueOf(code), description);
	}

}
