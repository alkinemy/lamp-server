package lamp.admin.domain.host.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.HostStatus;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.host.model.HostStatusCode;
import lamp.admin.domain.host.model.entity.HostStatusEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HostMonitoringService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private HostStatusEntityService hostStatusEntityService;

	@Async
	public void metricsMonitoring(Host host) {
		Optional<Agent> agentOptional = agentService.getAgentOptional(host.getId());
		HostStatusEntity hostStatusEntity = new HostStatusEntity();
		hostStatusEntity.setId(host.getId());
		if (agentOptional.isPresent()) {
			Agent agent = agentOptional.get();

			HostStatus hostStatus = getHostStatus(agent);
			BeanUtils.copyProperties(hostStatus, hostStatusEntity);
		} else {
			hostStatusEntity.setStatus(HostStatusCode.OUT_OF_SERVICE);
			hostStatusEntity.setLastStatusTime(new Date());
		}
		hostStatusEntityService.update(hostStatusEntity);
	}

	protected HostStatus getHostStatus(Agent agent) {
		HostStatus hostStatus = new HostStatus();
		hostStatus.setLastStatusTime(new Date());

		try {
			Map<String, Object> metrics = agentClient.getMetrics(agent);

			hostStatus.setCpuUser(getDoubleValue(metrics.get("server.cpu.user")));
			hostStatus.setCpuNice(getDoubleValue(metrics.get("server.cpu.nice")));
			hostStatus.setCpuSys(getDoubleValue(metrics.get("server.cpu.sys")));

			hostStatus.setDiskTotal(getLongValue(metrics.get("server.fileSystem.total")));
			hostStatus.setDiskUsed(getLongValue(metrics.get("server.fileSystem.used")));
			hostStatus.setDiskFree(getLongValue(metrics.get("server.fileSystem.free")));

			hostStatus.setMemTotal(getLongValue(metrics.get("server.mem.total")));
			hostStatus.setMemUsed(getLongValue(metrics.get("server.mem.used")));
			hostStatus.setMemFree(getLongValue(metrics.get("server.mem.free")));

			hostStatus.setSwapTotal(getLongValue(metrics.get("server.swap.total")));
			hostStatus.setSwapUsed(getLongValue(metrics.get("server.swap.used")));
			hostStatus.setSwapFree(getLongValue(metrics.get("server.swap.free")));

			log.debug("metrics = {}", metrics);

			if (hostStatus.getDiskUsedPercentage() > 70
				|| hostStatus.getMemUsedPercentage() > 70) {
				hostStatus.setStatus(HostStatusCode.DOWN);
			} else {
				hostStatus.setStatus(HostStatusCode.UP);
			}

		} catch (Exception e) {
			log.error("Agent metrics load failed", e);
			hostStatus.setStatus(HostStatusCode.UNKNOWN);
		}

		return hostStatus;
	}

	protected Double getDoubleValue(Object object) {
		if (object instanceof Double) {
			return (Double) object;
		} else if (object instanceof String) {
			return Double.parseDouble((String) object);
		}
		return null;

	}
	protected Long getLongValue(Object object) {
		if (object instanceof Long) {
			return (Long) object;
		} else if (object instanceof Integer) {
			return new Long((Integer)object);
		} else if (object instanceof String) {
			return Long.parseLong((String) object);
		}
		return null;
	}
}
