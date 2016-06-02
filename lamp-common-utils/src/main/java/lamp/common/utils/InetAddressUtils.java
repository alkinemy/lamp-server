package lamp.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InetAddressUtils {

	private static final Pattern RANGE_IP4_ADRESS_PATTERN = Pattern.compile("(^\\S*)\\[(\\d*)\\-(\\d*)\\](\\S*)");

	private InetAddressUtils() {
	}

	public static String getHostName(String address, String defaultHostName) {
		try {
			return InetAddress.getByName(address).getHostName();
		} catch (UnknownHostException e) {
			return defaultHostName;
		}
	}

	public static String getCanonicalHostName(String address, String defaultHostName) {
		try {
			return InetAddress.getByName(address).getCanonicalHostName();
		} catch (UnknownHostException e) {
			return defaultHostName;
		}
	}

	public static String getHostAddress(String address, String defaultAddress) {
		try {
			return InetAddress.getByName(address).getHostAddress();
		} catch (UnknownHostException e) {
			return defaultAddress;
		}
	}

	public static List<String> getRangeIP4Address(String rangeAddress) {
		if (rangeAddress == null) {
			return null;
		}

		String[] addressArray = rangeAddress.split("\\s*(,|\\s)\\s*");

		List<String> addresses = new ArrayList<>();
		for (String a : addressArray) {
			Matcher matcher = RANGE_IP4_ADRESS_PATTERN.matcher(a);
			boolean found = false;
			while (matcher.find()) {
				if (matcher.groupCount() == 4) {
					found = true;
					String beginStr = matcher.group(2);
					String endStr = matcher.group(3);
					int paddingLength = beginStr.length();
					String paddingFormat = "%0" + paddingLength + "d";
					int begin = Integer.parseInt(beginStr);
					int end = Integer.parseInt(endStr);
					for (int i = begin; i <= end; i++) {
						StringBuilder address = new StringBuilder();
						address.append(matcher.group(1));
						address.append(String.format(paddingFormat, i));
						address.append(matcher.group(4));

						addresses.add(address.toString());
					}
				}
			}
			if (!found) {
				addresses.add(a);
			}
		}

		return addresses;
	}
}
