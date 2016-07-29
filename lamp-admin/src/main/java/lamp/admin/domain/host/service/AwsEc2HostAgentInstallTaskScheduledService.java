package lamp.admin.domain.host.service;

import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.service.TaskService;
import lamp.admin.domain.host.model.task.AwsEc2HostAgentInstallTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AwsEc2HostAgentInstallTaskScheduledService {

	@Autowired
	private TaskService jobService;

	@Autowired
	private AwsEc2HostAgentInstallTaskExecuteService awsEc2HostAgentInstallTaskExecuteService;

	@Scheduled(cron="*/10 * * * * *")
	public void process() {
		log.debug("AwsEc2HostAgentInstallTask process");
		List<AwsEc2HostAgentInstallTask> tasks = jobService.getJobList(AwsEc2HostAgentInstallTask.class, TaskStatus.READY);
		for (AwsEc2HostAgentInstallTask task : tasks) {
			try {
				awsEc2HostAgentInstallTaskExecuteService.execute(task);
			} catch (Exception e) {
				log.error("AwsEc2HostAgentInstallTask execute failed", e);
			}
		}
	}

}
