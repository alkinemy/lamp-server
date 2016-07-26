package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.AwsCluster;
import lamp.admin.core.host.AwsEc2Host;
import lamp.admin.domain.app.base.model.AppInstanceDeployPolicy;
import lamp.admin.domain.app.base.model.task.AwsEc2AppInstanceDeployTask;
import lamp.admin.domain.base.service.TaskService;
import lamp.admin.domain.host.service.AwsEc2HostManagementService;
import lamp.admin.domain.host.service.ClusterService;
import lamp.admin.domain.host.service.form.AwsEc2HostsForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AwsEc2AppInstanceManagementService {

	@Autowired
	private ClusterService clusterService;

	@Autowired
	private AwsEc2HostManagementService awsEc2HostManagementService;

	@Autowired
	private AppService appService;

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	private TaskService taskService;

	public List<AppInstance> deploy(String path, String clusterId, AppInstanceDeployPolicy deployPolicy) {
		AwsCluster cluster = clusterService.getAwsCluster(clusterId);
		return deploy(path, cluster, null, deployPolicy);
	}

	public List<AppInstance> deploy(String path, AwsCluster cluster, AwsEc2HostsForm hostsForm, AppInstanceDeployPolicy deployPolicy) {
		App app = appService.getAppByPath(path);
		List<AwsEc2Host> hosts = awsEc2HostManagementService.runInstances(cluster, hostsForm);

		List<AppInstance> appInstances = appInstanceService.createAppInstances(hosts, app);

		for (AppInstance appInstance : appInstances) {
			AwsEc2AppInstanceDeployTask task = new AwsEc2AppInstanceDeployTask();
			task.setId(UUID.randomUUID().toString());
			task.setAppInstanceId(appInstance.getId());

			taskService.addTask(task);
		}

		return appInstances;
	}


}
