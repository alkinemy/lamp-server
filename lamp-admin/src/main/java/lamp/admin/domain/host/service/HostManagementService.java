package lamp.admin.domain.host.service;


import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.HostCredentials;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.host.model.*;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.host.service.form.HostCredentialsForm;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.admin.domain.host.service.form.ManagedHostCredentialsForm;
import lamp.admin.domain.host.service.form.ScannedHostCredentialsForm;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.FileUtils;
import lamp.common.utils.InetAddressUtils;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class HostManagementService {

	@Autowired
	private HostService hostService;

	@Autowired
	private HostScanService hostScanService;

	@Autowired
	private SshKeyService sshKeyService;

	@Autowired
	private HostAgentInstallService hostAgentInstallService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;

	public <T extends Host> List<T> addHosts(List<T> hosts) {
		return hostService.addHosts(hosts);
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
		hostCredentials.setAuthType(editForm.getAuthType());

		if (HostAuthType.PASSWORD.equals(editForm.getAuthType())) {
			hostCredentials.setPassword(editForm.getPassword());
		} else if (HostAuthType.KEY.equals(editForm.getAuthType())) {
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
		} else if (HostAuthType.STORED_KEY.equals(editForm.getAuthType())) {
			SshKey sshKey = sshKeyService.getSshKey(editForm.getSshKeyId(), true);

			if (StringUtils.isBlank(hostCredentials.getUsername())) {
				hostCredentials.setUsername(sshKey.getUsername());
			}
			hostCredentials.setPrivateKey(sshKey.getPrivateKey());
			hostCredentials.setPassphrase(sshKey.getPassphrase());
		} else {
			throw Exceptions.newException(AdminErrorCode.UNSUPPORTED_HOST_AUTH_TYPE, editForm.getAuthType());
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

	@Transactional
	public void shutdownAndDelete(Host host) {
		shutdownAgent(host.getId(), host.getStatus());
		delete(host);
	}

	public void delete(Host host) {
		hostService.removeHostEntity(host.getId());
	}

	public void shutdownAgent(String hostId, HostStatusCode hostStatusCode) {
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

}



