package lamp.admin.domain.host.service.form;

import lamp.admin.domain.host.model.HostCredentials;
import lamp.admin.domain.host.model.HostsConfiguration;
import lamp.admin.domain.host.model.ScannedHost;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class HostAgentInstallForm {

	private HostAgentRepositoryInfo agentRepositoryInfo;

	private List<ScannedHost> scannedHosts;
	private HostCredentials hostCredentials;
	private HostsConfiguration hostsConfiguration;

}
