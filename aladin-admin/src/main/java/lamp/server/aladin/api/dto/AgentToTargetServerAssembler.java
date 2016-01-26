package lamp.server.aladin.api.dto;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class AgentToTargetServerAssembler extends AbstractListAssembler<Agent, TargetServer> {

	@Override protected TargetServer doAssemble(Agent agent) {
		TargetServer targetServer = new TargetServer();
		targetServer.setName(agent.getName());
		targetServer.setHostname(agent.getHostname());
		targetServer.setAddress(agent.getAddress());
		targetServer.setAgentHealthUrl(agent.getProtocol(), agent.getAddress(), agent.getPort(), agent.getHealthPath());
		targetServer.setAgentMonitor(false);
		targetServer.setAgentInstalled(true);
		targetServer.setAgentInstallPath(agent.getAppDirectory());

		return targetServer;
	}

}
