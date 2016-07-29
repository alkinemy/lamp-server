package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.Map;

@Getter
@Setter
@ToString
public class AgentInstallResult {

	private Exception exception;
	private File installLogFile;

	private AgentInstallMetadata metadata;

	private String hostname;
	private String status;

	private Map<String, Object> parameters;

}
