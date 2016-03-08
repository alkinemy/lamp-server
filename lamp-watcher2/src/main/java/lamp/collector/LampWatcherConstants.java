package lamp.collector;

import java.nio.charset.Charset;

public abstract class LampWatcherConstants {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static final String SYSTEM_ACCOUNT = "system";

	public static final String FLASH_MESSAGE_KEY = "flashMessage";

	public static final String ACTION_KEY = "action";
	public static final String ACTION_CREATE = "create";
	public static final String ACTION_UPDATE = "update";
}
