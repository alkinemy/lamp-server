package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class TargetServerCreateForm {

	private Long id;
	@NotEmpty
	private String name;
	private String description;

	@NotEmpty
	private String hostname;
	@NotEmpty
	private String address;

	private String authType;
	private String username;
	private String password;

	private Boolean agentInstalled;
	@NotEmpty
	private String agentPath;

	private Boolean monitor;
	private Long monitorInterval;

}
