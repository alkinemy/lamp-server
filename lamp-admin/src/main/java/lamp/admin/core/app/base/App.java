package lamp.admin.core.app.base;

import lamp.admin.domain.app.base.model.entity.AppType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class App {

	@NonNull
	private String id;
	@NonNull
	private AppType type;
	@NonNull
	private String name;
	@NonNull
	private String path;
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

	private String version;

}
