package lamp.admin.core.agent.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SshKeyDto {

	private Long id;

	private String name;

	private String description;

	private String privateKey;

	private String username;

	private String password;

}
