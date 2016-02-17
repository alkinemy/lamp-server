package lamp.admin.core.app.domain;

public enum AppInstallCommandType {

	EXECUTE(Values.EXECUTE),

	CREATE_FILE(Values.CREATE_FILE),
	REMOVE_FILE(Values.REMOVE_FILE);

	private String typeName;

	AppInstallCommandType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static final class Values {
		public static final String EXECUTE = "EXECUTE";
		public static final String CREATE_FILE = "CREATE_FILE";
		public static final String REMOVE_FILE = "REMOVE_FILE";

		private Values() {
		}
	}

}
