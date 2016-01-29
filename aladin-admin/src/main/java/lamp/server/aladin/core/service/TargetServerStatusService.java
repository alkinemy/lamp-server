package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.HealthStatus;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.domain.TargetServerStatus;
import lamp.server.aladin.core.repository.TargetServerStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TargetServerStatusService {

	@Autowired
	private TargetServerStatusRepository targetServerStatusRepository;

	@Transactional
	public void updateStatus(TargetServer targetServer, HealthStatus agentStatus, LocalDateTime agentStatusTime) {
		TargetServerStatus targetServerStatus = targetServerStatusRepository.getOne(targetServer.getId());
		targetServerStatus.setAgentStatus(agentStatus);
		targetServerStatus.setAgentStatusDate(agentStatusTime);
	}
}
