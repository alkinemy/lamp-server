package lamp.admin.domain.agent.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.app.base.AppInstanceStatus;
import lamp.admin.core.app.base.AppInstanceStatusResult;
import lamp.admin.core.app.base.HealthCheck;
import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lamp.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Transient;
import java.util.*;
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
			HealthCheck healthCheck = appInstance.getHealthCheck();
			if (healthCheck == null) {
				AppInstanceStatusResult statusResult =
					Optional.ofNullable(appInstanceStatusMap.get(appInstance.getId()))
						.orElse(new AppInstanceStatusResult(AppInstanceStatus.UNKNOWN, "Not managed AppInstance"));
				appInstanceService.updateStatus(appInstance.getId(), statusResult);
			} else {
				AppInstanceStatusResult statusResult = healthChecker.getStatusResult(appInstance, healthCheck);
				appInstanceService.updateStatus(appInstance.getId(), statusResult);
			}
		}
	}


}
