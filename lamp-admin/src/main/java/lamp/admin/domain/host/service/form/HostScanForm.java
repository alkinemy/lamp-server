package lamp.admin.domain.host.service.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class HostScanForm {

	private String clusterId;

	@NotEmpty
	private String hostnames;

	private int sshPort = 22;

}
