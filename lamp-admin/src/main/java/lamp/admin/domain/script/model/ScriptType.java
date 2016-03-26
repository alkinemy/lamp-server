package lamp.admin.domain.script.model;

public enum ScriptType {

	INSTALL(Values.INSTALL);

	private String typeName;

	ScriptType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static final class Values {
		public static final String INSTALL = "INSTALL";


		private Values() {
		}
	}

}
