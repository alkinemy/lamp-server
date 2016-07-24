package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class AgentInstallResult {

	private AgentInstallMetadata metadata;

	private String hostname;
	private String status;

	private String error;

	private Map<String, Object> parameters;

}
