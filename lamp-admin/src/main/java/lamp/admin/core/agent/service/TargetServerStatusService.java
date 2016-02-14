package lamp.admin.core.agent.service;

import lamp.admin.core.agent.domain.TargetServer;
import lamp.admin.core.agent.domain.TargetServerStatus;
import lamp.admin.core.agent.repository.TargetServerStatusRepository;
import lamp.admin.core.monitoring.domain.HealthStatus;
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
