package lamp.admin.core.app.base;

import lamp.admin.core.app.docker.DockerAppContainer;
import lamp.admin.core.app.jar.JarAppContainer;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lombok.Getter;

public enum AppContainerType {

	SIMPLE(Names.SIMPLE, Values.SIMPLE),
	JAR(Names.JAR, Values.JAR),
	DOCKER(Names.DOCKER, Values.DOCKER);

	@Getter
	private String typeName;
	@Getter
	private Class<?> typeValue;

	AppContainerType(String typeName, Class<?> typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public static final class Names {
		public static final String SIMPLE = "simple";
		public static final String JAR = "jar";
		public static final String DOCKER = "docker";

		private Names() {
		}
	}

	public static final class Values {
		public static final Class<?> SIMPLE = SimpleAppContainer.class;
		public static final Class<?> JAR = JarAppContainer.class;
		public static final Class<?> DOCKER = DockerAppContainer.class;

		private Values() {
		}
	}

}
