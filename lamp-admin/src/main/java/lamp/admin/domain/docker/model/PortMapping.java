package lamp.admin.domain.docker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PortMapping {

	private Integer containerPort;
	private Integer hostPort;
	private String protocol;
	private String name;

}
