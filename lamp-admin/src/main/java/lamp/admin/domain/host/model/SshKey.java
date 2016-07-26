package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SshKey {

	private String id;
	private String name;
	private String description;

	private String username;
	private String privateKey;
	private String passphrase;

}
