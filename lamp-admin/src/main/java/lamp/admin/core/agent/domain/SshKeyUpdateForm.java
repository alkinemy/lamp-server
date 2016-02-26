package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class SshKeyUpdateForm {

	@NotEmpty
	private String name;

	private String description;

	private String privateKey;

	private String username;

	private String password;

}
