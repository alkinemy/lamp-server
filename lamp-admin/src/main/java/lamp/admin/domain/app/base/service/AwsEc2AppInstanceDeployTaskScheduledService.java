package lamp.admin.domain.app.base.service;

import lamp.admin.domain.app.base.model.task.AwsEc2AppInstanceDeployTask;
import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.service.TaskService;
import lamp.admin.domain.host.model.task.AwsEc2HostAgentInstallTask;
import lamp.admin.domain.host.service.AwsEc2HostAgentInstallTaskExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AwsEc2AppInstanceDeployTaskScheduledService {

	@Autowired
	private TaskService jobService;

	@Autowired
	private AwsEc2AppInstanceDeployTaskExecuteService awsEc2AppInstanceDeployTaskExecuteService;

	@Scheduled(cron="*/10 * * * * *")
	public void process() {
		log.debug("AwsEc2AppInstanceDeployTask process");
		List<AwsEc2AppInstanceDeployTask> tasks = jobService.getJobList(AwsEc2AppInstanceDeployTask.class, TaskStatus.READY);
		for (AwsEc2AppInstanceDeployTask task : tasks) {
			try {
				awsEc2AppInstanceDeployTaskExecuteService.execute(task);
			} catch (Exception e) {
				log.error("AwsEc2AppInstanceDeployTask execute failed", e);
			}
		}
	}

}
