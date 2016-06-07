package lamp.admin.domain.docker.service;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.model.TargetServer;
import lamp.admin.domain.agent.model.TargetServerDto;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.agent.service.TargetServerService;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.docker.model.DockerApp;
import lamp.admin.domain.docker.model.DockerAppDeployForm;
import lamp.admin.domain.docker.model.DockerAppEntity;
import lamp.admin.domain.docker.repository.DockerAppEntityRepository;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DockerAppDeployService {

	@Autowired
	private AgentClient agentClient;
	@Autowired
	private DockerAppService dockerAppService;
	@Autowired
	private AgentService agentService;


	public void deployApp(DockerApp dockerApp, Agent agent) {
//		agentClient.deployApp(agent, dockerApp);
	}

	public void deployApp(String appId, DockerAppDeployForm editForm) {
		DockerApp app = dockerAppService.getDockerApp(appId);
		for (String targetServerId : editForm.getTargetServerIds()) {
			Agent agent = agentService.getAgentByTargetServerId(targetServerId);
			deployApp(app, agent);
		}
	}

}
