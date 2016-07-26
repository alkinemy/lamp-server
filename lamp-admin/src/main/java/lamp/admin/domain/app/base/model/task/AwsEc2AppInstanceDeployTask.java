package lamp.admin.domain.app.base.model.task;

import lamp.admin.domain.base.model.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AwsEc2AppInstanceDeployTask implements Task {

	private String id;

	private String appInstanceId;

}
