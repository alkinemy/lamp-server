package lamp.admin.domain.app.base.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppContainer;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.resource.repo.service.AppResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AppDeployService {

	@Autowired
	private AppService appService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private AgentClient agentClient;

	@Autowired
	private AppResourceLoader appResourceLoader;

	public void deploy(String appId, List<String> hostsIds) {
		App app = appService.getApp(appId);
		Resource resource = null;
		if (app.getContainer() instanceof SimpleAppContainer) {
			resource = appResourceLoader.getResource(((SimpleAppContainer) app.getContainer()).getAppResource());
		}

		List<String> hostIds = hostsIds;
		for (String hostId : hostIds) {
			Agent agent = agentService.getAgent(hostId);

			String instanceId = UUID.randomUUID().toString();

			// FIXME 복제하도록 수정
			AppContainer appContainer = app.getContainer();
			if (appContainer instanceof SimpleAppContainer) {
				((SimpleAppContainer)appContainer).setId(instanceId);
			}

			agentClient.deployApp(agent, app.getId(), appContainer, resource);
		}

	}




}
