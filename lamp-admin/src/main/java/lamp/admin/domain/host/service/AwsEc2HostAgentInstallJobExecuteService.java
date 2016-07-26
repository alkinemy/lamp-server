package lamp.admin.domain.host.service;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.google.common.collect.Lists;
import lamp.admin.core.host.AwsCluster;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.service.TaskService;
import lamp.admin.domain.host.model.AgentInstallResult;
import lamp.admin.domain.host.model.HostAuthType;
import lamp.admin.domain.host.model.task.AwsEc2HostAgentInstallTask;
import lamp.admin.domain.host.service.form.ManagedHostCredentialsForm;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AwsEc2HostAgentInstallJobExecuteService {

	@Autowired
	private ClusterService clusterService;

	@Autowired
	private HostFacadeService hostFacadeService;

	@Autowired
	private HostScanService hostScanService;

	@Autowired
	private AwsEc2HostManagementService awsEc2HostManagementService;

	@Autowired
	private TaskService jobService;

	public void execute(AwsEc2HostAgentInstallTask task) {
		log.debug("AwsEc2HostAgentInstallTask execute : {}", task);
		Host host = hostFacadeService.getHost(task.getHostId());
		AwsCluster cluster = clusterService.getAwsCluster(host.getClusterId());
		Instance instance = awsEc2HostManagementService.getEC2Instance(cluster, task.getInstanceId());
		InstanceState instanceState = instance.getState();

		if (instanceState.getCode() == 16) {
			ScannedHost scannedHost = hostScanService.scanHost(instance.getPrivateIpAddress(), cluster.getSshPort());
			log.debug("ScannedHost : {}", scannedHost);
			if (scannedHost.isConnected()) {
				if (scannedHost.isManaged()) {
					jobService.updateJobStatus(task.getId(), TaskStatus.COMPLETED);
				} else {
					try {
						jobService.updateJobStatus(task.getId(), TaskStatus.STARTED);

						ManagedHostCredentialsForm hostCredentialsForm = new ManagedHostCredentialsForm();
						hostCredentialsForm.setClusterId(cluster.getId());
						hostCredentialsForm.setAuthType(HostAuthType.STORED_KEY);
						hostCredentialsForm.setSshKeyId(cluster.getSshKeyId());
						hostCredentialsForm.setSshPort(cluster.getSshPort());
						hostCredentialsForm.setHostId(Lists.newArrayList(host.getId()));

						List<AgentInstallResult> result = hostFacadeService.installAgents(hostCredentialsForm);
						jobService.updateJobStatus(task.getId(), TaskStatus.COMPLETED);
						if (StringUtils.isNotBlank(task.getNextTaskId())) {
							jobService.updateJobStatus(task.getNextTaskId(), TaskStatus.READY);
						}
					} catch (Exception e) {
						log.error("AgentInstall Failed", e);
						jobService.updateJobStatus(task.getId(), TaskStatus.FAILED);
					}
				}
			}
		}

	}
}
