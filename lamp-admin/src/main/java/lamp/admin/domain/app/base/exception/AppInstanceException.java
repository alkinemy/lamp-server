package lamp.admin.domain.app.base.exception;

import lamp.admin.core.app.base.AppInstanceStatus;
import lombok.Getter;

public class AppInstanceException extends RuntimeException {

	@Getter
	private AppInstanceStatus status;

	public AppInstanceException(AppInstanceStatus status, Exception e) {
		super(e);
		this.status = status;
	}

}
