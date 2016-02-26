package lamp.watch.server.core.domain;

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
public class AppHealthStatus {

	@Enumerated(EnumType.STRING)
	@Column(name = "health_status")
	private AppHealthStatusCode code;

	@Column(name = "health_description")
	private String description;

	public static AppHealthStatus of(String code, String description) {
		return of(AppHealthStatusCode.valueOf(code), description);
	}

}
