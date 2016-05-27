package lamp.admin.domain.docker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortDefinition {

	private Integer port;
	private String protocol;
	private String name;


}
