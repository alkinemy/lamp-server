package lamp.monitoring.core.notify.mms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MmsHttpNotifierProperties {

	private String method = "post";
	private String url;

	private String contentType = "application/json";
	private String content;
	private String charset = "UTF-8";

}
