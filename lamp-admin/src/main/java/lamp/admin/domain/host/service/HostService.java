package lamp.admin.domain.host.service;


import lamp.admin.LampAdminConstants;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.domain.host.model.*;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.host.service.form.HostCredentialsForm;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.common.utils.InetAddressUtils;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HostService {

	@Autowired
	private HostEntityService hostEntityService;

	@Autowired
	private HostScanService hostScanService;

	@Autowired
	private HostAgentInstallService hostAgentInstallService;

	@Autowired
	private SmartAssembler smartAssembler;

	public List<Host> getHosts(String clusterId) {
		return smartAssembler.assemble(hostEntityService.getHostEntityList(clusterId), HostEntity.class, Host.class);
	}

	public List<ScannedHost> scanHost(HostScanForm form) {
		List<ScannedHost> scannedHosts = new ArrayList<>();
		List<String> hostnames = InetAddressUtils.getRangeIP4Address(form.getHostnames());
		for (String host : hostnames) {
			ScannedHost scannedHost = hostScanService.scanHost(host, form.getSshPort());
			scannedHosts.add(scannedHost);
		}
		return scannedHosts;
	}

	public void installAgents(String clusterId, HostCredentialsForm editForm) throws IOException {
		HostCredentials hostCredentials = new HostCredentials();
		hostCredentials.setUsername(editForm.getUsername());
		hostCredentials.setUsePassword(editForm.isUsePassword());

		HostConfiguration hostConfiguration = new HostConfiguration();

		String agentFile = "classpath:agent/lamp-agent.jar";

		if (editForm.isUsePassword()) {
			hostCredentials.setPassword(editForm.getPassword());
		} else {
			// TODO file size check
			hostCredentials.setPrivateKey(new String(editForm.getPrivateKey().getBytes(), LampAdminConstants.DEFAULT_CHARSET));
			hostCredentials.setPassphrase(editForm.getPassphrase());
		}

		for (String address : editForm.getScannedHostAddress()) {
			TargetHost targetHost = new TargetHost();
			targetHost.setId(UUID.randomUUID().toString());
			targetHost.setClusterId(clusterId);
			targetHost.setHostname(InetAddressUtils.getHostName(address, address));
			targetHost.setAddress(address);

			hostAgentInstallService.installAgent(targetHost, hostCredentials, agentFile, hostConfiguration);
		}
	}

}



