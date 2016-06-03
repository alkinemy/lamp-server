package lamp.admin.domain.docker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DockerAppDeployForm {

	private String id;

	private String[] ids;

	private List<String> targetServerIds;
}
