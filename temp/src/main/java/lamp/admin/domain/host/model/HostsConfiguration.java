package lamp.admin.domain.host.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HostsConfiguration {

	private String appDirectory;

	private String javaHome;

	private String hostNetworkInterfaceCollectionFilter = "^lo$";
	private String hostFsCollectionFilter = "^$";
	private String hostDiskCollectionFilter = "^$";

}
