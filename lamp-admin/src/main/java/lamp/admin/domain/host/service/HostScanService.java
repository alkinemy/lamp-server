package lamp.admin.domain.host.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.core.host.scan.HostScanner;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.model.AgentInfo;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.host.model.AgentInstallProperties;
import lamp.admin.domain.host.model.HostManagedStatus;
import lamp.admin.domain.host.model.entity.HostEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

@Slf4j
@Service
public class HostScanService {

	private HostScanner hostScanner = new HostScanner();

	@Autowired
	private AgentInstallProperties agentInstallProperties;

	@Autowired
	private HostService hostService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;

	public ScannedHost scanHost(String host, int port) {
		ScannedHost scannedHost = hostScanner.scanHost(host, port);
		HostManagedStatus managed = scanHostManagedStatus(scannedHost.getAddress());
		scannedHost.setManaged(managed);
		return scannedHost;
	}

	protected HostManagedStatus scanHostManagedStatus(String address) {
		AgentInfo agentInfo = null;
		try {
			agentInfo = agentClient.getAgentInfo(address, agentInstallProperties.getPort());
		} catch (ResourceAccessException e) { //TODO exception에 따라서 처리
			log.debug("Agent is not installed", e);
			return HostManagedStatus.NOT_MANAGED;
		}

		if (isLampAgent(agentInfo)) { //TODO Agent가 정상적으로 설치된거면(정보확인)
			Optional<Agent> agent = agentService.getAgentOptional(agentInfo.getId());
			if (!agent.isPresent()) {
				return HostManagedStatus.MISSING;
			}

			Optional<HostEntity> host = hostService.getHostEntityOptionalByAddress(address);
			if (!host.isPresent()) {
				return HostManagedStatus.MISSING;
			}
			return HostManagedStatus.MANAGED;
		} else {
			return HostManagedStatus.NOT_MANAGED;
		}
	}

	private boolean isLampAgent(AgentInfo agentInfo) {
		return agentInstallProperties.getArtifactId().equals(agentInfo.getArtifactId())
			&& agentInstallProperties.getGroupId().equals(agentInfo.getGroupId());
	}

}



