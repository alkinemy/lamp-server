package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AgentInstallResult {

	private String hostname;
	private String address;
	private String status;

	private String error;

}
