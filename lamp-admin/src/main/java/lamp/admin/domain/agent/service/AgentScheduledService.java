package lamp.admin.domain.agent.service;

import lamp.admin.domain.agent.model.Agent;
import lamp.collector.core.service.health.HealthProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class AgentScheduledService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentAppInstanceStatusService agentAppInstanceStatusService;

	@Scheduled(cron = "0/10 * * * * *")
	public void process() {
		Collection<Agent> agents = agentService.getAgents();
		agents.forEach(agentAppInstanceStatusService::appInstancesStatusUpdate);
	}

}
