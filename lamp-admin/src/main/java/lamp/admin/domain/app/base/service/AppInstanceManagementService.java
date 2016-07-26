package lamp.admin.domain.app.base.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.*;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.app.base.model.AppInstanceDeployPolicy;
import lamp.admin.domain.host.service.HostFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AppInstanceManagementService {

	@Autowired
	private AppService appService;
	@Autowired
	private AppInstanceService appInstanceService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentClient agentClient;

	@Autowired
	private HostFacadeService hostFacadeService;

	@Autowired
	private AppInstanceDeployService appInstanceDeployService;

	public List<AppInstance> deploy(String path, List<String> hostsIds, AppInstanceDeployPolicy deployPolicy) {
		App app = appService.getAppByPath(path);

		List<AppInstance> appInstances = createAppInstancesByHostIds(hostsIds, app);

		appInstanceDeployService.deploy(app, appInstances, deployPolicy);

		return appInstances;
	}

	public List<AppInstance> createAppInstancesByHostIds(List<String> hostsIds, App app) {
		List<Host> hosts = new ArrayList<>();
		List<String> hostIds = hostsIds;
		for (String hostId : hostIds) {
			Host host = hostFacadeService.getHost(hostId);
			hosts.add(host);
		}
		return appInstanceService.createAppInstances(hosts, app);
	}


	public void redeploy(String path, List<String> instanceIds, AppInstanceDeployPolicy deployPolicy) {
		App app = appService.getAppByPath(path);

		List<AppInstance> appInstances = new ArrayList<>();
		for (String instanceId : instanceIds) {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);
			populateAppInstance(app, appInstance);
			appInstance.setStatus(AppInstanceStatus.DEPLOYING);

			appInstanceService.updateAppInstance(appInstance);

			appInstances.add(appInstance);
		}

		appInstanceDeployService.redeploy(app, appInstances, deployPolicy);
	}

	protected void populateAppInstance(App app, AppInstance appInstance) {
		appInstance.setName(app.getName());
		appInstance.setAppName(app.getName());
		appInstance.setAppVersion(app.getVersion());
		appInstance.setTags(app.getTags());

		Host host = hostFacadeService.getHost(appInstance.getHostId());
		appInstance.setClusterId(host.getClusterId());
		appInstance.setClusterName(host.getClusterName());
		appInstance.setHostName(host.getName());
		appInstance.setHostAddress(host.getAddress());

		AppContainer appContainer = app.getContainer();
		appInstanceService.populateAppContainer(appContainer, appInstance);
	}


	public void undeploy(String path, List<String> instanceIds) {
		App app = appService.getAppByPath(path);

		List<AppInstance> appInstances = new ArrayList<>();
		for (String instanceId : instanceIds) {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);
			appInstance.setStatus(AppInstanceStatus.UNDEPLOYING);

			appInstanceService.updateAppInstance(appInstance);

			appInstances.add(appInstance);

		}

		appInstanceDeployService.undeploy(app, appInstances);
	}


	public void start(String path, List<String> instanceIds) {
		App app = appService.getAppByPath(path);

		for (String instanceId : instanceIds) {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);
			appInstance.setStatus(AppInstanceStatus.STARTING);
			appInstanceService.updateAppInstance(appInstance);

			Agent agent = agentService.getAgent(appInstance.getHostId());
			agentClient.start(agent, appInstance.getId());
		}
	}

	public void stop(String path, List<String> instanceIds) {
		App app = appService.getAppByPath(path);

		for (String instanceId : instanceIds) {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);
			appInstance.setStatus(AppInstanceStatus.STARTING);
			appInstanceService.updateAppInstance(appInstance);

			Agent agent = agentService.getAgent(appInstance.getHostId());
			agentClient.stop(agent, appInstance.getId());
		}
	}


	public void transferStdOutStream(AppInstance appInstance, OutputStream outputStream) {
		Agent agent = agentService.getAgent(appInstance.getHostId());
		agentClient.transferLogStream(agent, appInstance.getId(), "stdout", outputStream);
	}

	public void transferStdErrStream(AppInstance appInstance, OutputStream outputStream) {
		Agent agent = agentService.getAgent(appInstance.getHostId());
		agentClient.transferLogStream(agent, appInstance.getId(), "stderr", outputStream);
	}




}
