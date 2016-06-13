package lamp.admin.domain.app.base.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.agent.AgentResponseErrorException;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppContainer;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.core.app.docker.DockerAppContainer;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.resource.repo.service.AppResourceLoader;
import lamp.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AppInstanceDeployService {

	@Autowired
	private AppService appService;
	@Autowired
	private AppInstanceService appInstanceService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentClient agentClient;

	@Autowired
	private AppResourceLoader appResourceLoader;

	public List<AppInstance> deploy(String appId, List<String> hostsIds) {
		App app = appService.getApp(appId);
		Resource resource = null;
		if (app.getContainer() instanceof SimpleAppContainer) {
			resource = appResourceLoader.getResource(((SimpleAppContainer) app.getContainer()).getAppResource());
		}

		List<AppInstance> appInstances = new ArrayList<>();
		List<String> hostIds = hostsIds;
		for (String hostId : hostIds) {
			String instanceId = UUID.randomUUID().toString();

			AppInstance appInstance = newAppInstance(app, instanceId, hostId);
			appInstance.setStatus(AppInstanceStatus.DEPLOYING);

			appInstanceService.create(appInstance);
			try {
				Agent agent = agentService.getAgent(hostId);
				AppContainer appContainer = newAppContainer(app, instanceId);

				agentClient.deployApp(agent, app.getId(), appContainer, resource);

				appInstance.setStatus(AppInstanceStatus.STARTING);
				try {
					agentClient.start(agent, instanceId);
				} catch (Exception e) {
					log.warn("App start failed", e);
					appInstance.setStatus(AppInstanceStatus.START_FAILED);
					appInstance.setStatusMessage(ExceptionUtils.getStackTrace(e));
				}
			} catch (Exception e) {
				log.warn("App deploy failed", e);
				appInstance.setStatus(AppInstanceStatus.DEPLOY_FAILED);
				appInstance.setStatusMessage(ExceptionUtils.getStackTrace(e));
			}

			appInstanceService.update(appInstance);
			appInstances.add(appInstance);
		}
		return appInstances;
	}


	public void undeploy(AppInstance appInstance, boolean forceStop) {
		Agent agent = agentService.getAgent(appInstance.getHostId());
		try {
			agentClient.undeployApp(agent, appInstance.getId(), forceStop);
			appInstanceService.delete(appInstance);
		} catch (AgentResponseErrorException e) {
			if ("APP_INSTANCE_NOT_FOUND".equals(e.getAgentError().getCode())) {
				log.debug("undeploy : APP_INSTANCE_NOT_FOUND =", e);
				appInstanceService.delete(appInstance);
			} else {
				throw e;
			}
		}
	}

	public void start(AppInstance appInstance) {
		Agent agent = agentService.getAgent(appInstance.getHostId());
		agentClient.start(agent, appInstance.getId());
	}

	public void stop(AppInstance appInstance) {
		Agent agent = agentService.getAgent(appInstance.getHostId());
		agentClient.stop(agent, appInstance.getId());
	}

	protected AppContainer newAppContainer(App app, String instanceId) {
		try {
			AppContainer appContainer = app.getContainer().getClass().newInstance();
			BeanUtils.copyProperties(app.getContainer(), appContainer);
			if (appContainer instanceof SimpleAppContainer) {
				((SimpleAppContainer)appContainer).setId(instanceId);
			} else if (appContainer instanceof DockerAppContainer) {
				((DockerAppContainer)appContainer).setId(instanceId);
			}
			return appContainer;
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.APP_CONTAINER_CREATE_FAILED, app.getId());
		}

	}

	protected AppInstance newAppInstance(App app, String instanceId, String hostId) {

		AppInstance appInstance = new AppInstance();
		appInstance.setId(instanceId);
		appInstance.setName(app.getName());
		appInstance.setAppId(app.getId());
		appInstance.setAppVersion(app.getVersion());
		appInstance.setHostId(hostId);

		return appInstance;
	}


}
