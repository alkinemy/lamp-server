package lamp.admin.core.app.base;

import lamp.admin.domain.app.base.model.entity.AppType;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class App {

	private String id;
	private AppType type;
	private String name;
	private String path;
	private String parentPath;
	private String description;

	private String clusterId;
	private int cpu;
	private int memory;
	private int diskSpace;
	private int instances;
	private int runningInstances;

	private String status;
	private String health;

	private AppContainer container;
	private Map<String, Object> parameters;

	private String version;

	public App(String id, AppType type, String name, String parentPath) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.parentPath = parentPath;
	}

}
