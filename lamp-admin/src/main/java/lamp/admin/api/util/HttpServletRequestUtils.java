package lamp.admin.api.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {

	public static String getClientAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (isUnknown(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isUnknown(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isUnknown(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (isUnknown(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (isUnknown(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	protected static boolean isUnknown(String ip) {
		return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
	}

	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

}
