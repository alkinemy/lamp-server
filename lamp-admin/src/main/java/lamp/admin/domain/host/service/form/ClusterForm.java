package lamp.admin.domain.host.service.form;

import lamp.admin.core.host.ClusterType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class ClusterForm {

	private String name;
	private String description;

	private ClusterType type = ClusterType.DEFAULT;

	private String tenantId;

	private String username;
	private boolean usePassword = true;

	private String password;
	private String passwordConfirm;

	private MultipartFile privateKey;
	private String passphrase;
	private String passphraseConfirm;

	private String sshKeyId;
	private int sshPort = 22;

	// AWS
	private String accessKeyId;
	private String secretAccessKey;

	private String imageId;
	private String instanceType;
	private String keyName;
	private String subnetId;
	private String securityGroupIds;

	private String userData;

}
