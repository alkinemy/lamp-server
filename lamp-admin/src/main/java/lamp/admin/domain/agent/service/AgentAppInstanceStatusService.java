package lamp.admin.domain.agent.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.app.base.service.AppInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentAppInstanceStatusService {

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private AppInstanceService appInstanceService;

	public void appInstancesStatusUpdate(Agent agent) {
		List<AppInstance> appInstances = agentClient.getAppInstances(agent);
		appInstances.stream().forEach(appInstanceService::updateStatus);
	}

}
