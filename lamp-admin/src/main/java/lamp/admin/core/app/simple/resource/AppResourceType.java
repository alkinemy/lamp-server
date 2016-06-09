package lamp.admin.core.app.simple.resource;

import lombok.Getter;

public enum AppResourceType {

	LOCAL(Values.LOCAL),
	MAVEN(Values.MAVEN),
	URL(Values.URL);

	@Getter
	private String value;

	AppResourceType(String value) {
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
