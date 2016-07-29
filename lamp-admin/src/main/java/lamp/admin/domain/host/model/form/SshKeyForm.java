package lamp.admin.domain.host.model.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class SshKeyForm {

	private String name;
	private String description;

	private String username;

	private MultipartFile privateKey;

	private boolean passphraseChange = true;
	private String passphrase;
	private String passphraseConfirm;

}
