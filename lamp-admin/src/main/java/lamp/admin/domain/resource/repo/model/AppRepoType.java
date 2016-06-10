package lamp.admin.domain.resource.repo.model;

import lombok.Getter;

public enum AppRepoType {

	LOCAL(Values.LOCAL),
	MAVEN(Values.MAVEN),
	URL(Values.URL);

	@Getter
	private String value;

	AppRepoType(String value) {
		this.value = value;
	}

	public static final class Values {
		public static final String LOCAL = "LOCAL";
		public static final String MAVEN = "MAVEN";
		public static final String URL = "URL";

		private Values() {
		}
	}

}
