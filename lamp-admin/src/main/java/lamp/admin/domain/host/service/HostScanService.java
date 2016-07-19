package lamp.admin.domain.host.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.core.host.scan.HostScanner;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.host.model.AgentInstallProperties;
import lamp.admin.domain.host.model.entity.HostEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class HostScanService {

	private HostScanner hostScanner = new HostScanner();

	@Autowired
	private AgentInstallProperties agentInstallProperties;

	@Autowired
	private HostEntityService hostEntityService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;

	public ScannedHost scanHost(String host, int port) {
		ScannedHost scannedHost = hostScanner.scanHost(host, port);
		boolean managed = scanHostManaged(scannedHost.getAddress());
		scannedHost.setManaged(managed);
		return scannedHost;
	}

	protected boolean scanHostManaged(String address) {
//		AgentInfo agentInfo = null;
//		try {
//			agentInfo = agentClient.getAgentInfo(address, agentInstallProperties.getPort());
//		} catch (Exception e) { //TODO exception에 따라서 처리
//			Optional<Agent> agentOptional = agentService.getOptionalByAddress(address);
//			if (agentOptional.isPresent()) {
//				Agent agent = agentOptional.get();
//				agentService.delete(agent);
//				hostEntityService.delete(agent.getId());
//				return false;
//			}
//
//			Optional<HostEntity> hostEntityOptional = hostEntityService.getOptionalByAddress(address);
//			if (hostEntityOptional.isPresent()) {
//				String id = hostEntityOptional.get().getId();
//				hostEntityService.delete(id);
//			}
//
//			return false;
//		}
//
//		if (agentInstallProperties.getArtifactId().equals(agentInfo.getArtifactId())) { //TODO Agent가 정상적으로 설치된거면(정보확인)
//			Optional<Agent> agent = agentService.getAgentOptional(agentInfo.getId());
//			if (!agent.isPresent()) {
//				agentService.register(null); //TODO agent 등록
//			}
//
//			Optional<HostEntity> host = hostEntityService.getOptionalByAddress(address);
//			if (!host.isPresent()) {
//				hostEntityService.create(null); //TODO host 등록
//			}
//			return true;
//		} else {
//			agentService.deregister(agentInfo.getId());
//			hostEntityService.delete(agentInfo.getId());
//			return false;
//		}
		Optional<HostEntity> hostEntityOptional = hostEntityService.getOptionalByAddress(address);
		if (hostEntityOptional.isPresent()) {
			// FIXME 제대로 구현 바람
			return true;
		} else {

		}
		//
		agentInstallProperties.getPort();
		return false;
	}

}



