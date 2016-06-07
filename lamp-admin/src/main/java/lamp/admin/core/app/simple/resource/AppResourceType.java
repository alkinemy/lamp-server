package lamp.admin.core.app.simple.resource;

import lombok.Getter;

public enum AppResourceType {

	LOCAL(Names.LOCAL),
	MAVEN(Names.MAVEN),
	URL(Names.URL);

	@Getter
	private String typeName;

	AppResourceType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static final class Names {
		public static final String LOCAL = "local";
		public static final String MAVEN = "maven";
		public static final String URL = "url";

		private Names() {
		}
	}

}
