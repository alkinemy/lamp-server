package lamp.admin.domain.host.service;


import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.HostCredentials;
import lamp.admin.core.host.HostStatus;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.host.model.*;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.host.service.form.HostCredentialsForm;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.FileUtils;
import lamp.common.utils.InetAddressUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class HostService {

	@Autowired
	private HostEntityService hostEntityService;

	@Autowired
	private HostScanService hostScanService;

	@Autowired
	private HostAgentInstallService hostAgentInstallService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private SmartAssembler smartAssembler;


	public List<Host> getHosts() {
		return smartAssembler.assemble(hostEntityService.getList(), HostEntity.class, Host.class);
	}

	public List<Host> getHostsByClusterId(String clusterId) {
		if (StringUtils.isBlank(clusterId)) {
			return getHosts();
		}
		return smartAssembler.assemble(hostEntityService.getList(clusterId), HostEntity.class, Host.class);
	}

	public Host getHost(String id) {
		Optional<Host> hostOptional = getHostOptional(id);
		Exceptions.throwsException(!hostOptional.isPresent(), AdminErrorCode.HOST_NOT_FOUND, id);
		return hostOptional.get();
	}

	public Optional<Host> getHostOptional(String id) {
		HostEntity hostEntity = hostEntityService.get(id);
		Host host =  smartAssembler.assemble(hostEntity, HostEntity.class, Host.class);
		return Optional.ofNullable(host);
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

	public List<AgentInstallResult> installAgents(HostCredentialsForm editForm) throws IOException {
		HostCredentials hostCredentials = new HostCredentials();
		hostCredentials.setUsername(editForm.getUsername());
		hostCredentials.setUsePassword(editForm.isUsePassword());

		HostConfiguration hostConfiguration = new HostConfiguration();

		String agentFile = editForm.getAgentFile();

		if (editForm.isUsePassword()) {
			hostCredentials.setPassword(editForm.getPassword());
		} else {
			// TODO file size check
			if (editForm.getPrivateKey() != null && !editForm.getPrivateKey().isEmpty()) {
				hostCredentials.setPrivateKey(new String(editForm.getPrivateKey().getBytes(), LampAdminConstants.DEFAULT_CHARSET));
				hostCredentials.setPassphrase(editForm.getPassphrase());
			} else {
				String userHome = System.getProperty("user.home");
				File file = new File(userHome, ".ssh/id_rsa");
				hostCredentials.setPrivateKey(FileUtils.readFileToString(file, LampAdminConstants.DEFAULT_CHARSET));
				hostCredentials.setPassphrase(editForm.getPassphrase());
			}
		}

		List<AgentInstallResult> results = new ArrayList<>();
		for (String address : editForm.getScannedHostAddress()) {
			TargetHost targetHost = new TargetHost();
			targetHost.setId(UUID.randomUUID().toString());
			targetHost.setClusterId(editForm.getClusterId());
			targetHost.setHostname(InetAddressUtils.getHostName(address, address));
			targetHost.setAddress(address);

			// TODO Async
			AgentInstallResult result = hostAgentInstallService.installAgent(targetHost, hostCredentials, agentFile, hostConfiguration);
			results.add(result);
		}
		return results;
	}

	public void remove(String hostId) {
		HostEntity hostEntity = hostEntityService.get(hostId);
		HostStatusCode hostStatusCode = hostEntity.getStatus();
		Optional<Agent> agentOptional = agentService.getAgentOptional(hostEntity.getId());
		boolean hostManaged = HostStatusCode.UP.equals(hostStatusCode)
			|| HostStatusCode.DOWN.equals(hostStatusCode);
		if (agentOptional.isPresent()) {
			Agent agent = agentOptional.get();

			if (hostManaged) {
				agentClient.shutdown(agent);
			}
			agentService.deregister(agent.getId());
		}

		hostEntityService.delete(hostEntity.getId());
	}
}



