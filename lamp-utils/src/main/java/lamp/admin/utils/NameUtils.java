package lamp.admin.utils;


public class NameUtils {

	public static String name(String... names) {
		StringBuilder sb = new StringBuilder();
		for (String name : names) {
			sb.append(name);
		}
		return sb.toString();
	}
}
