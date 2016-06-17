package lamp.admin.domain.host.service;

import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class HostScheduledService {

	@Autowired
	private HostMonitoringService hostMonitoringService;

	@Autowired
	private HostService hostService;

	public void hostMetricsMonitoring() {
		List<Host> hosts = hostService.getHosts();
		hosts.stream().forEach(hostMonitoringService::metricsMonitoring);
	}

}
