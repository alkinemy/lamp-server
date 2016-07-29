package lamp.admin.domain.host.model.form;

import lamp.admin.domain.host.model.HostAuthType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public abstract class HostCredentialsForm {

	private String clusterId;
	private String agentFile;

	private String username;
	private HostAuthType authType = HostAuthType.PASSWORD;
	@Deprecated
	private boolean usePassword;

	private String password;
	private String passwordConfirm;

	private MultipartFile privateKey;
	private String passphrase;
	private String passphraseConfirm;

	private String sshKeyId;

	private int sshPort = 22;

}
