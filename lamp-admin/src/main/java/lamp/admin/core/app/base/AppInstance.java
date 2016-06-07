package lamp.admin.core.app.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppInstance {

	private String id;
	private String name;
	private String description;

	private App app;

	private String pid;
	private String status;
	private String correctStatus;

	private boolean isMonitor;

}
