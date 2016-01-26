package lamp.server.aladin.admin.support;

import lombok.Getter;

public enum FlashMessageLevel {

	SUCCESS("alert-success"), INFO("alert alert-info"), WARNING("alert alert-warning"), ERROR("alert-danger");

	@Getter
	private String cssClass;

	FlashMessageLevel(String cssClass) {
		this.cssClass = cssClass;
	}
}
