package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.AwsCluster;
import lamp.admin.core.host.AwsEc2Host;
import lamp.admin.domain.app.base.model.AppInstanceDeployPolicy;
import lamp.admin.domain.app.base.model.task.AwsEc2AppInstanceDeployTask;
import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.service.TaskService;
import lamp.admin.domain.host.service.AwsEc2HostManagementService;
import lamp.admin.domain.host.service.ClusterService;
import lamp.admin.domain.host.model.form.AwsEc2HostsForm;
import lamp.admin.domain.host.service.HostFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
	private HostFacadeService hostFacadeService;

	@Autowired
	private AppService appService;

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	private TaskService taskService;

	public List<AppInstance> deploy(String path, String clusterId, int minCount, int maxCount, AppInstanceDeployPolicy deployPolicy) {
		AwsCluster cluster = clusterService.getAwsCluster(clusterId);
		AwsEc2HostsForm hostsForm = new AwsEc2HostsForm();
		BeanUtils.copyProperties(cluster, hostsForm);
		hostsForm.setMinCount(minCount);
		hostsForm.setMaxCount(maxCount);
		return deploy(path, cluster, hostsForm, deployPolicy);
	}

	public List<AppInstance> deploy(String path, AwsCluster cluster, AwsEc2HostsForm hostsForm, AppInstanceDeployPolicy deployPolicy) {
		App app = appService.getAppByPath(path);
		List<AwsEc2Host> hosts = hostFacadeService.addHosts(cluster, hostsForm);

		List<AppInstance> appInstances = appInstanceService.createAppInstances(hosts, app);

		for (AppInstance appInstance : appInstances) {
			AwsEc2AppInstanceDeployTask task = new AwsEc2AppInstanceDeployTask();
			task.setId(UUID.randomUUID().toString());
			task.setAppInstanceId(appInstance.getId());

			taskService.addTask(task, TaskStatus.CREATED);
		}

		return appInstances;
	}

	public void undeploy(String path, List<String> instanceIds) {
		App app = appService.getAppByPath(path);

//		awsEc2HostManagementService.terminateInstances();
	}
}
