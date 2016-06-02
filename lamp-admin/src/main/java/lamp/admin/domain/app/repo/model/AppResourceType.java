package lamp.admin.domain.app.repo.model;

public enum AppResourceType {

	NONE(Values.NONE),

	LOCAL(Values.LOCAL),
	MAVEN(Values.MAVEN),
	URL(Values.URL);

	private String typeName;

	AppResourceType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static final class Values {
		public static final String NONE = "NONE";
		public static final String LOCAL = "LOCAL";
		public static final String MAVEN = "MAVEN";
		public static final String URL = "URL";

		private Values() {
		}
	}

}
