package lamp.admin.domain.host.service.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class HostCredentialsForm {

	private List<String> scannedHostAddress;

	private String username;
	private boolean usePassword = true;

	private String password;
	private String passwordConfirm;

	private MultipartFile privateKey;
	private String passphrase;
	private String passphraseConfirm;

	private int sshPort = 22;

	private int parallelInstallCount;

}
