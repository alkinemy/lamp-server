package lamp.admin.web.service;

import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.agent.domain.TargetServer;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.agent.service.TargetServerService;
import lamp.admin.core.monitoring.service.AgentHealthCheckService;
import lamp.admin.core.monitoring.service.AgentMetricCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class AgentScheduledCheckService {

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentHealthCheckService agentHealthCheckService;

	@Autowired
	private AgentMetricCollectService agentMetricCollectService;

	public void checkHealth() {
		Collection<TargetServer> targetServers = targetServerService.getTargetServerList();
		for (TargetServer targetServer : targetServers) {
			agentHealthCheckService.checkHealth(targetServer);
		}
	}

	public void collectMetrics() {
		Collection<Agent> agents = agentService.getAgentList();
		for (Agent agent : agents) {
			agentMetricCollectService.collectMetrics(agent);
		}
	}

}
