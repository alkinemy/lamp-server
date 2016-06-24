package lamp.admin.domain.agent.service;

import lamp.admin.domain.agent.model.Agent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class AgentScheduledService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentAppInstanceHealthCheckService agentAppInstanceHealthCheckService;

	public void appInstanceStatusUpdate() {
		Collection<Agent> agents = agentService.getAgents();
		agents.forEach(agentAppInstanceHealthCheckService::healthCheck);
	}

}
