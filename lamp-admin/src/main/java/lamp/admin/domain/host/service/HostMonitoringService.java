package lamp.admin.domain.host.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentAppInstanceStatusService;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.host.model.entity.HostStatusEntity;
import lombok.extern.slf4j.Slf4j;
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
		if (agentOptional.isPresent()) {
			Agent agent = agentOptional.get();
			Map<String, Object> metrics = agentClient.getMetrics(agent);

			HostStatusEntity hostStatus = new HostStatusEntity();
			hostStatus.setId(host.getId());
			hostStatus.setLastStatusTime(new Date());

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

			hostStatusEntityService.update(hostStatus);

			log.error("metrics = {}, {}", host.getName(), metrics);
		} else {
			log.warn("Host({})의 Agent를 찾을 수 없습니다.", host.getName());
		}
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
