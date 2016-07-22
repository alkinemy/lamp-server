package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TargetHost {

	private String id;
	private String clusterId;

	private String name;
	private String address;

}
