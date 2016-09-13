package lamp.admin.domain.app.base.model.form;

import lamp.admin.core.app.docker.Parameter;
import lamp.admin.core.app.docker.PortDefinition;
import lamp.admin.core.app.docker.PortMapping;
import lamp.admin.core.app.docker.Volume;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class DockerAppCreateForm {

	private String id;
	private String name;
	private String description;

	private String image;
	private String hostName;
	private String network;

	private boolean forcePullImage;
//	private boolean privileged;

//	private List<PortDefinition> portDefinitions;
//	private List<PortMapping> portMappings;
//	private List<Volume> volumes;
//	private Map<String, String> env;


	private String portMappings;
	private String volumes;
	private String env;

	private String entrypoint;
	private String cmd;

	private List<Parameter> parameters = new ArrayList<>();

}
