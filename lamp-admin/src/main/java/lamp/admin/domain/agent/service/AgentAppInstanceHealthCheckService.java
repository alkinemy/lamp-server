package lamp.admin.domain.agent.service;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.core.app.base.AppInstanceStatusResult;
import lamp.admin.core.app.base.HealthEndpoint;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AgentAppInstanceHealthCheckService {

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	private AgentAppInstanceHealthChecker healthChecker;

	@Async
	public void healthCheck(Agent agent) {
		log.debug("appInstancesStatusUpdate : {}", agent.getId());
		List<AppInstance> appInstances = appInstanceService.getAppInstancesByHostId(agent.getId());
		Map<String, AppInstanceStatusResult> appInstanceStatusMap = new HashMap<>();
		try {
			List<AppInstance> appInstancesByAgent = appInstanceService.getAppInstancesByAgent(agent);
			appInstanceStatusMap.putAll(
				appInstancesByAgent
					.stream()
					.collect(Collectors.toMap(
						i -> i.getId(),
						i -> new AppInstanceStatusResult(i.getStatus(), i.getStatusMessage()))));
		} catch (Exception e) {
			log.warn("Get AppInstances From Agent failed", e);
			appInstanceStatusMap.putAll(
			appInstances
				.stream()
				.collect(Collectors.toMap(
					i -> i.getId(),
					i -> new AppInstanceStatusResult(AppInstanceStatus.UNKNOWN, e.getMessage()))));
		}

		healthCheck(appInstances, appInstanceStatusMap);
	}

	protected void healthCheck(List<AppInstance> appInstances, Map<String, AppInstanceStatusResult> appInstanceStatusMap) {
		for (AppInstance appInstance : appInstances) {
			HealthEndpoint healthEndpoint = appInstance.getHealthEndpoint();
			if (healthEndpoint == null) {
				AppInstanceStatusResult statusResult =
					Optional.ofNullable(appInstanceStatusMap.get(appInstance.getId()))
						.orElse(new AppInstanceStatusResult(AppInstanceStatus.UNKNOWN, "Not managed AppInstance"));
				appInstanceService.updateStatus(appInstance.getId(), statusResult);
			} else {
				AppInstanceStatusResult statusResult = healthChecker.getStatusResult(appInstance, healthEndpoint);
				appInstanceService.updateStatus(appInstance.getId(), statusResult);
			}
		}
	}


}
