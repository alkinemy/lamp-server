package lamp.admin.core.app.docker;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lamp.admin.core.app.base.AppContainer;
import lamp.admin.core.app.base.AppContainerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonTypeName(AppContainerType.Names.DOCKER)
public class DockerAppContainer implements AppContainer {

	private String id;
	private String image;
	private String network;

	private boolean forcePullImage;
	private boolean privileged;

	private List<PortDefinition> portDefinitions;

	private List<PortMapping> portMappings = new ArrayList<>();

	private List<Parameter> parameters = new ArrayList<>();

	private List<Volume> volumes = new ArrayList<>();

	private Map<String, String> env;

}
