package lamp.admin.core.app.docker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DockerAppContainer implements AppContainer {

	private String name;

	private String image;
	private String network;

	private boolean forcePullImage;
//	private boolean privileged;

	private List<String> portMappings;

	private List<String> volumes;

	private List<String> env;


//	private List<Parameter> parameters = new ArrayList<>();

}
