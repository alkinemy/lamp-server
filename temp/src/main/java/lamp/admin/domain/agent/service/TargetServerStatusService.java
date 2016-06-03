package lamp.admin.domain.agent.service;

import lamp.admin.domain.agent.model.TargetServer;
import lamp.admin.domain.agent.model.TargetServerStatus;
import lamp.admin.domain.agent.repository.TargetServerStatusRepository;
import lamp.admin.domain.monitoring.model.HealthStatus;
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
