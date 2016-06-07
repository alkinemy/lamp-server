package lamp.admin.domain.host.service;

import lamp.admin.core.host.ScannedHost;
import lamp.admin.core.host.scan.HostScanner;
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

	public ScannedHost scanHost(String host, int port) {
		ScannedHost scannedHost = hostScanner.scanHost(host, port);
		boolean managed = scanHostManaged(host);
		scannedHost.setManaged(managed);
		return scannedHost;
	}

	protected boolean scanHostManaged(String address) {
		Optional<HostEntity> hostEntityOptional = hostEntityService.getHostEntityOptionalByAddress(address);
		if (hostEntityOptional.isPresent()) {
			// FIXME 제대로 구현 바람
			return true;
		} else {

		}
		//
		agentInstallProperties.getAgentPort();
		return false;
	}

}



