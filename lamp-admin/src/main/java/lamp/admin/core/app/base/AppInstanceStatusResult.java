package lamp.admin.core.app.base;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppInstanceStatusResult {

	private AppInstanceStatus status;
	private String statusMessage;

	public AppInstanceStatusResult(AppInstanceStatus status) {
		this(status, null);
	}

	public AppInstanceStatusResult(AppInstanceStatus status, String statusMessage) {
		this.status = status;
		this.statusMessage = statusMessage;
	}
}
