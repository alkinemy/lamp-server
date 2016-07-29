package lamp.admin;

import java.nio.charset.Charset;

public abstract class LampAdminConstants {

	public static final String DEFAULT_CHARSET_NAME = "UTF-8";
	public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

	public static final String SYSTEM_ACCOUNT = "system";

	public static final String FLASH_MESSAGE_KEY = "flashMessage";

	public static final String ACTION_KEY = "action";
	public static final String ACTION_CREATE = "create";
	public static final String ACTION_UPDATE = "update";
}
