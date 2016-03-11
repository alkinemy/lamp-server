package lamp.admin.core.agent.domain;

import lamp.collector.core.domain.TargetHealthType;
import lamp.collector.core.domain.TargetMetricsType;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AgentToTargetServerAssembler extends AbstractListAssembler<Agent, TargetServer> implements Populater<Agent, TargetServer> {

	@Override protected TargetServer doAssemble(Agent agent) {
		TargetServer targetServer = new TargetServer();
		targetServer.setId(agent.getHostname());
		targetServer.setName(agent.getName());
		targetServer.setHostname(agent.getHostname());
		targetServer.setAddress(agent.getAddress());

		targetServer.setAgentGroupId(agent.getGroupId());
		targetServer.setAgentArtifactId(agent.getArtifactId());
		targetServer.setAgentVersion(agent.getVersion());

		targetServer.setHealthMonitoringEnabled(true);
		targetServer.setHealthCollectionEnabled(true);
		targetServer.setHealthType(StringUtils.defaultIfBlank(agent.getHealthType(), TargetHealthType.SPRING_BOOT));
		targetServer.setHealthUrl(agent.getHealthUrl());

		targetServer.setMetricsMonitoringEnabled(true);
		targetServer.setMetricsCollectionEnabled(true);
		targetServer.setMetricsType(StringUtils.defaultIfBlank(agent.getMetricsType(), TargetMetricsType.SPRING_BOOT));
		targetServer.setMetricsUrl(agent.getMetricsUrl());

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

		targetServer.setHealthType(StringUtils.defaultIfBlank(agent.getHealthType(), TargetHealthType.SPRING_BOOT));
		targetServer.setHealthUrl(agent.getHealthUrl());

		targetServer.setMetricsType(StringUtils.defaultIfBlank(agent.getMetricsType(), TargetMetricsType.SPRING_BOOT));
		targetServer.setMetricsUrl(agent.getMetricsUrl());
	}
}
