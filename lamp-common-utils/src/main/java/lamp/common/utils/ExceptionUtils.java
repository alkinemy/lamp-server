package lamp.common.utils;

public abstract class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {
	public static String getStackTrace(Throwable t, int length) {
		String stackTrace = getStackTrace(t);
		return StringUtils.substring(stackTrace, 0, length);
	}
}
