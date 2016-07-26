package lamp.admin.domain.host.model;

import lombok.Getter;

@Getter
public enum HostManagedStatus {

	MANAGED("관리됨"),
	NOT_MANAGED("관리되지 않음"),
	MISSING("미아");

	private String message;

	HostManagedStatus(String message) {
		this.message = message;
	}

}
