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
	private String groupId;

	private int cpu;
	private int memory;
	private int diskSpace;
	private int instances;

	private AppContainer container;

}
