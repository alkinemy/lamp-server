package lamp.admin.core.app.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class App {

	private String id;
	private String name;
	private String description;
	private String path;

	private int cpu;
	private int memory;
	private int diskSpace;
	private int instances;
	private int runningInstances;

	private String status;
	private String health;

	private AppContainer container;

}
