package lamp.admin.core.agent.domain;

import lamp.admin.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class AgentToTargetServerAssembler extends AbstractListAssembler<Agent, TargetServer> {

	@Override protected TargetServer doAssemble(Agent agent) {
		TargetServer targetServer = new TargetServer();
		targetServer.setName(agent.getName());
		targetServer.setHostname(agent.getHostname());
		targetServer.setAddress(agent.getAddress());
		targetServer.setAgentHealthUrl(agent.getHealthUrl());
		targetServer.setAgentMonitor(false);
		targetServer.setAgentInstalled(true);
		targetServer.setAgentInstallPath(agent.getAppDirectory());

		return targetServer;
	}

}
