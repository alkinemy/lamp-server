package lamp.admin.core.host;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class Cluster {

	private String id;
	private String name;
	private String description;

	private ClusterType type;

	private String tenantId;

	private String sshKeyId;
	private int sshPort = 22;
}
