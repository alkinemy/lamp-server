package lamp.admin.core.app.simple.resource;

import lamp.admin.core.app.docker.DockerAppContainer;
import lamp.admin.core.app.jar.JarAppContainer;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lombok.Getter;

public enum AppResourceType {

	LOCAL(Names.LOCAL, Values.LOCAL),
	MAVEN(Names.MAVEN, Values.MAVEN),
	URL(Names.URL, Values.URL);

	@Getter
	private String typeName;
	@Getter
	private Class<?> typeValue;

	AppResourceType(String typeName, Class<?> typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
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

	public static final class Values {
		public static final Class<?> LOCAL = SimpleAppContainer.class;
		public static final Class<?> MAVEN = JarAppContainer.class;
		public static final Class<?> URL = DockerAppContainer.class;

		private Values() {
		}
	}

}
