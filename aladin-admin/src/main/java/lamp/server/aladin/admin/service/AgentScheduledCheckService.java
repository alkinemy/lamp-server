package lamp.server.aladin.admin.service;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.service.AgentHealthCheckService;
import lamp.server.aladin.core.service.AgentMetricCollectService;
import lamp.server.aladin.core.service.AgentService;
import lamp.server.aladin.core.service.TargetServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
