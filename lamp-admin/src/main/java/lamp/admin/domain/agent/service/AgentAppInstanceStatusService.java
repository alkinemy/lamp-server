package lamp.admin.domain.agent.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AgentAppInstanceStatusService {

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private AppInstanceService appInstanceService;

	@Async
	public void appInstancesStatusUpdate(Agent agent) {
		try {
			List<AppInstance> appInstances = agentClient.getAppInstances(agent);
			appInstances.stream().forEach(appInstanceService::updateStatus);
		} catch (Exception e) {
			log.warn("AppInstances status update failed", e);
		}
	}

}
