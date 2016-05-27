package lamp.admin.domain.host.service;


import lamp.admin.domain.host.model.HostCredentials;
import lamp.admin.domain.host.model.HostsConfiguration;
import lamp.admin.domain.host.model.ScannedHost;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.common.utils.InetAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostService {
	
	@Autowired
	private HostScanService hostScanService;

	public List<ScannedHost> scanHost(HostScanForm form) {
		List<ScannedHost> scannedHosts = new ArrayList<>();
		List<String> hostnames = InetAddressUtils.getRangeIP4Address(form.getHostnames());
		for (String host : hostnames) {
			ScannedHost scannedHost = scanHost(host, form.getSshPort());
			scannedHosts.add(scannedHost);
		}
		return scannedHosts;
	}

	public ScannedHost scanHost(String address, int sshPort) {
		return hostScanService.scanHost(address, sshPort);
	}

	public void addHost(ScannedHost scannedHost, HostCredentials hostCredentials, HostsConfiguration hostsConfiguration) {


	}

}



