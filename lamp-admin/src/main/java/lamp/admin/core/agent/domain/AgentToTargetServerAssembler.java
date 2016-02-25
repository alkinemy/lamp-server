package lamp.admin.core.agent.domain;

import lamp.admin.utils.assembler.AbstractListAssembler;
import lamp.admin.utils.assembler.Populater;
import org.springframework.stereotype.Component;

@Component
public class AgentToTargetServerAssembler extends AbstractListAssembler<Agent, TargetServer> implements Populater<Agent, TargetServer> {

	@Override protected TargetServer doAssemble(Agent agent) {
		TargetServer targetServer = new TargetServer();
		targetServer.setName(agent.getName());
		targetServer.setHostname(agent.getHostname());
		targetServer.setAddress(agent.getAddress());
		targetServer.setAgentGroupId(agent.getGroupId());
		targetServer.setAgentArtifactId(agent.getArtifactId());
		targetServer.setAgentVersion(agent.getVersion());

		targetServer.setAgentHealthCheckEnabled(false);
		targetServer.setAgentHealthType(agent.getHealthType());
		targetServer.setAgentHealthUrl(agent.getHealthUrl());

		targetServer.setAgentMetricsCollectEnabled(false);
		targetServer.setAgentMetricsType(agent.getMetricsType());
		targetServer.setAgentMetricsUrl(agent.getMetricsUrl());

		targetServer.setAgentMonitor(false);
		targetServer.setAgentInstalled(true);
		targetServer.setAgentInstallPath(agent.getAppDirectory());

		return targetServer;
	}

	@Override public void populate(Agent agent, TargetServer targetServer) {
		targetServer.setAddress(agent.getAddress());
		targetServer.setAgentInstalled(true);
		targetServer.setAgentInstallPath(agent.getAppDirectory());
		targetServer.setAgentGroupId(agent.getGroupId());
		targetServer.setAgentArtifactId(agent.getArtifactId());
		targetServer.setAgentVersion(agent.getVersion());

		targetServer.setAgentHealthType(agent.getHealthType());
		targetServer.setAgentHealthUrl(agent.getHealthUrl());

		targetServer.setAgentMetricsType(agent.getMetricsType());
		targetServer.setAgentMetricsUrl(agent.getMetricsUrl());
	}
}
