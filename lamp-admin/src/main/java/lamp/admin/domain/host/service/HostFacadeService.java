package lamp.admin.domain.host.service;


import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.HostCredentials;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.host.model.AgentInstallResult;
import lamp.admin.domain.host.model.HostConfiguration;
import lamp.admin.domain.host.model.HostStatusCode;
import lamp.admin.domain.host.model.TargetHost;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.host.service.form.HostCredentialsForm;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.admin.domain.host.service.form.ManagedHostCredentialsForm;
import lamp.admin.domain.host.service.form.ScannedHostCredentialsForm;
import lamp.common.utils.FileUtils;
import lamp.common.utils.InetAddressUtils;
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
public class HostFacadeService {

	@Autowired
	private HostService hostService;

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
		return hostService.getHostList();
	}

	public List<Host> getHostsByClusterId(String clusterId) {
		return hostService.getHostListByClusterId(clusterId);
	}

	public Host getHost(String id) {
		return hostService.getHost(id);
	}

	public Optional<Host> getHostOptional(String id) {
		return hostService.getHostOptional(id);
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
		HostCredentials hostCredentials = getHostCredentials(editForm);

		HostConfiguration hostConfiguration = new HostConfiguration();

		String agentFile = editForm.getAgentFile();

		List<TargetHost> targetHosts = null;
		if (editForm instanceof ScannedHostCredentialsForm) {
			targetHosts = getTargetHosts((ScannedHostCredentialsForm) editForm);
		} else if (editForm instanceof ManagedHostCredentialsForm) {
			targetHosts = getTargetHosts((ManagedHostCredentialsForm) editForm);
		}

		return installAgents(targetHosts, hostCredentials, agentFile, hostConfiguration);
	}

	protected List<TargetHost> getTargetHosts(ScannedHostCredentialsForm editForm) {
		List<TargetHost> targetHosts = new ArrayList<>();
		for (String address : editForm.getScannedHostAddress()) {
			TargetHost targetHost;
			Optional<HostEntity> hostEntityOptional = hostService.getHostEntityOptionalByAddress(address);
			if (hostEntityOptional.isPresent()) {
				targetHost = newTargetHost(hostEntityOptional.get());
			} else {
				targetHost = new TargetHost();
				targetHost.setId(UUID.randomUUID().toString());
				targetHost.setClusterId(editForm.getClusterId());
				targetHost.setName(InetAddressUtils.getHostName(address, address));
				targetHost.setAddress(address);
			}
			targetHosts.add(targetHost);
		}
		return targetHosts;
	}

	protected List<TargetHost> getTargetHosts(ManagedHostCredentialsForm editForm) {
		List<TargetHost> targetHosts = new ArrayList<>();
		for (String hostId : editForm.getHostId()) {
			HostEntity hostEntity = hostService.getHostEntity(hostId);
			TargetHost targetHost = newTargetHost(hostEntity);

			targetHosts.add(targetHost);
		}
		return targetHosts;
	}

	protected TargetHost newTargetHost(HostEntity hostEntity) {
		TargetHost targetHost = new TargetHost();
		targetHost.setId(hostEntity.getId());
		targetHost.setClusterId(hostEntity.getClusterId());
		targetHost.setName(hostEntity.getName());
		targetHost.setAddress(hostEntity.getAddress());
		return targetHost;
	}

	protected HostCredentials getHostCredentials(HostCredentialsForm editForm) throws IOException {
		HostCredentials hostCredentials = new HostCredentials();
		hostCredentials.setUsername(editForm.getUsername());
		hostCredentials.setUsePassword(editForm.isUsePassword());

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
		return hostCredentials;
	}

	protected List<AgentInstallResult> installAgents(List<TargetHost> targetHosts, HostCredentials hostCredentials, String agentFile, HostConfiguration hostConfiguration) throws IOException {
		List<AgentInstallResult> results = new ArrayList<>();
		for (TargetHost targetHost : targetHosts) {
			// TODO Async
			Optional<HostEntity> hostEntityOptional = hostService.getHostEntityOptional(targetHost.getId());
			if (hostEntityOptional.isPresent()) {
				HostEntity hostEntity = hostEntityOptional.get();
				shutdownAgent(hostEntity.getId(), hostEntity.getStatus());
			}

			AgentInstallResult result = hostAgentInstallService.installAgent(targetHost, hostCredentials, agentFile, hostConfiguration);
			results.add(result);
		}
		return results;
	}

	public void remove(String hostId) {
		HostEntity hostEntity = hostService.getHostEntity(hostId);

		shutdownAgent(hostEntity.getId(), hostEntity.getStatus());

		hostService.removeHostEntity(hostEntity.getId());
	}

	protected void shutdownAgent(String hostId, HostStatusCode hostStatusCode) {
		Optional<Agent> agentOptional = agentService.getAgentOptional(hostId);
		boolean hostManaged = HostStatusCode.UP.equals(hostStatusCode)
			|| HostStatusCode.DOWN.equals(hostStatusCode);
		if (agentOptional.isPresent()) {
			Agent agent = agentOptional.get();

			if (hostManaged) {
				log.info("Agent shutdown : {}", hostId);
				agentClient.shutdown(agent);
			}
			agentService.deregister(agent.getId());
		}
	}


	protected void shutdownAgent(Agent agent, HostStatusCode hostStatusCode) {
		boolean hostManaged = HostStatusCode.UP.equals(hostStatusCode)
			|| HostStatusCode.DOWN.equals(hostStatusCode);
		if (agent != null) {
			if (hostManaged) {
				agentClient.shutdown(agent);
			}
			agentService.deregister(agent.getId());
		}
	}

}



