package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.domain.app.base.model.AppInstanceDeployPolicy;
import lamp.admin.domain.app.base.model.task.AwsEc2AppInstanceDeployTask;
import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AwsEc2AppInstanceDeployTaskExecuteService {

	@Autowired
	private TaskService taskService;

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	private AppInstanceDeployService appInstanceDeployService;

	@Async
	public void execute(AwsEc2AppInstanceDeployTask task) {
		log.debug("AwsEc2AppInstanceDeployTask execute : {}", task);

		AppInstance appInstance = appInstanceService.getAppInstance(task.getAppInstanceId());
		AppInstanceDeployPolicy deployPolicy = null;

		try {
			appInstanceDeployService.deployAndStart(appInstance);
			taskService.updateTaskStatus(task.getId(), TaskStatus.COMPLETED);
		} catch (Exception e) {
			appInstanceService.updateAppInstanceStatus(appInstance, AppInstanceStatus.DEPLOY_FAILED, e.getMessage());
			taskService.updateTaskStatus(task.getId(), TaskStatus.FAILED);
		}

	}

}
