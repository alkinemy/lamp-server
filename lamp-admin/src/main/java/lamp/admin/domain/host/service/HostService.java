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
			Optional<HostEntity> hostEntityOptional = hostEntityService.getOptionalByAddress(address);
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
			HostEntity hostEntity = hostEntityService.get(hostId);
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
			shutdownAgent(targetHost.getId());

			AgentInstallResult result = hostAgentInstallService.installAgent(targetHost, hostCredentials, agentFile, hostConfiguration);
			results.add(result);
		}
		return results;
	}

	public void remove(String hostId) {
		HostEntity hostEntity = shutdownAgent(hostId);

		hostEntityService.delete(hostEntity.getId());
	}

	protected HostEntity shutdownAgent(String hostId) {
		HostEntity hostEntity = hostEntityService.get(hostId);
		HostStatusCode hostStatusCode = hostEntity.getStatus();
		Optional<Agent> agentOptional = agentService.getAgentOptional(hostEntity.getId());
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
		return hostEntity;
	}

}



