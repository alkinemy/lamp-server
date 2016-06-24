package lamp.admin.domain.app.base.model.entity;

import lombok.Getter;

public enum AppType {

	GROUP("group"), SIMPLE_APP("simple-app"), SPRING_BOOT_APP("spring-boot-app"), DOCKER_APP("docker-app");

	@Getter
	private String value;

	AppType(String value) {
		this.value = value;
	}

}
