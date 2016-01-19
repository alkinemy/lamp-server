package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppDto {

	private String id;
	private String name;
	private String description;

	private String appGroupId;
	private String appId;
	private String appName;
	private String appVersion;

	private String processType;
	private String pid;
	private String status;
	private String correctStatus;

	private boolean isMonitor;

}
