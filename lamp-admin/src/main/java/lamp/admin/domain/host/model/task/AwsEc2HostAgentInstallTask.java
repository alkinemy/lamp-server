package lamp.admin.domain.host.model.task;

import lamp.admin.domain.base.model.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AwsEc2HostAgentInstallTask implements Task {

	private String id;

	private String hostId;
	private String instanceId;

	private String nextTaskId;
}
