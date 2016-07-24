package lamp.admin.domain.host.service;

import lamp.admin.core.host.Host;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class HostScheduledService {

	@Autowired
	private HostMetricsProcessService hostMetricsProcessService;

	@Autowired
	private HostFacadeService hostFacadeService;

	public void hostMetricsMonitoring() {
		List<Host> hosts = hostFacadeService.getHosts();
		hosts.stream().forEach(hostMetricsProcessService::processMetrics);
	}

}
