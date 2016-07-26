package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.app.base.model.AppInstanceDeployPolicy;
import lamp.admin.domain.app.base.model.task.AwsEc2AppInstanceDeployTask;
import lamp.admin.domain.host.model.task.AwsEc2HostAgentInstallTask;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AwsEc2AppInstanceDeployTaskExecuteService {

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	private AppInstanceDeployService appInstanceDeployService;

	public void execute(AwsEc2AppInstanceDeployTask task) {
		log.debug("AwsEc2AppInstanceDeployTask execute : {}", task);

		AppInstance appInstance = appInstanceService.getAppInstance(task.getAppInstanceId());
		AppInstanceDeployPolicy deployPolicy = null;

		appInstanceDeployService.deploy(null, appInstance, deployPolicy);

	}

}
