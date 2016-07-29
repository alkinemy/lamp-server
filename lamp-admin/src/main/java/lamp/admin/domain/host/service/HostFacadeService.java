package lamp.admin.domain.host.service;


import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.*;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.base.service.TaskService;
import lamp.admin.domain.host.model.AgentInstallResult;
import lamp.admin.domain.host.model.HostStateCode;
import lamp.admin.domain.host.model.HostStatusCode;
import lamp.admin.domain.host.model.task.AwsEc2HostAgentInstallTask;
import lamp.admin.domain.host.model.form.AwsEc2HostsForm;
import lamp.admin.domain.host.model.form.HostCredentialsForm;
import lamp.admin.domain.host.model.form.HostScanForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class HostFacadeService {

	@Autowired
	private HostService hostService;

	@Autowired
	private HostManagementService hostManagementService;

	@Autowired
	private AwsEc2HostManagementService awsEc2HostService;

	@Autowired
	private ClusterService clusterService;

	@Autowired
	private TaskService jobService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;


	public List<Host> getHosts() {
		return hostService.getHostList();
	}

	public List<Host> getHostsByClusterId(String clusterId) {
		return hostService.getHostListByClusterId(clusterId);
	}

	public Cluster getCluster(String clusterId) {
		return clusterService.getCluster(clusterId);
	}

	public AwsCluster getAwsCluster(String clusterId) {
		return clusterService.getAwsCluster(clusterId);
	}

	public Host getHost(String id) {
		return hostService.getHost(id);
	}

	public Optional<Host> getHostOptional(String id) {
		return hostService.getHostOptional(id);
	}

	@Transactional
	public List<AwsEc2Host> addHosts(String clusterId, AwsEc2HostsForm editForm) {
		AwsCluster cluster = getAwsCluster(clusterId);
		return addHosts(cluster, editForm);
	}

	@Transactional
	public List<AwsEc2Host> addHosts(AwsCluster cluster, AwsEc2HostsForm editForm) {
		List<AwsEc2Host> hosts = awsEc2HostService.runInstances(cluster, editForm);

		List<AwsEc2Host> awsEc2Hosts = addHosts(hosts);
		for (AwsEc2Host awsEc2Host : awsEc2Hosts) {
			AwsEc2HostAgentInstallTask job = new AwsEc2HostAgentInstallTask();
			job.setId(UUID.randomUUID().toString());
			job.setHostId(awsEc2Host.getId());
			job.setInstanceId(awsEc2Host.getInstanceId());

			jobService.addTask(job);
		}
		return awsEc2Hosts;
	}

	public <T extends Host> List<T> addHosts(List<T> hosts) {
		return hostManagementService.addHosts(hosts);
	}

	public List<ScannedHost> scanHost(HostScanForm form) {
		return hostManagementService.scanHost(form);
	}

	public List<AgentInstallResult> installAgents(HostCredentialsForm editForm) throws IOException {
		return hostManagementService.installAgents(editForm);
	}

	@Transactional
	public void updateHostState(Host host, HostStateCode state) {
		host.setState(state);
		hostService.updateHost(host);
	}

	@Transactional
	public void deleteHost(String hostId) {
		Host host = getHost(hostId);
		if (host instanceof AwsEc2Host) {
			hostManagementService.shutdownAgent(host.getId(), host.getStatus());

			AwsCluster cluster = getAwsCluster(host.getClusterId());
			awsEc2HostService.terminateInstances(cluster, (AwsEc2Host) host);

			hostManagementService.delete(host);
		} else {
			hostManagementService.shutdownAndDelete(host);
		}
	}

	protected void shutdownAgent(Agent agent, HostStatusCode hostStatusCode) {
		boolean hostManaged = HostStatusCode.UP.equals(hostStatusCode)
			|| HostStatusCode.DOWN.equals(hostStatusCode);
		if (agent != null) {
			if (hostManaged) {
				agentClient.shutdown(agent);
			}
			agentService.deregister(agent.getId());
		}
	}


}



