package lamp.admin.core.host;

import lamp.admin.domain.host.model.HostAuthType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HostCredentials {

	private String username;

	private HostAuthType authType;

	private String password;

	private String privateKey;
	private String passphrase;

	private String sshKeyId;

	private int sshPort = 22;

}
