package lamp.admin.domain.agent.model;

import lamp.admin.domain.agent.service.SshKeyService;
import lamp.admin.web.monitoring.service.AgentHealthService;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TargetServerDtoAssembler extends AbstractListAssembler<TargetServer, TargetServerDto> {

	@Autowired
	private SshKeyService sshKeyService;

	@Autowired
	private AgentHealthService agentHealthService;

	@Override protected TargetServerDto doAssemble(TargetServer targetServer) {
		TargetServerDto targetServerDto = new TargetServerDto();
		BeanUtils.copyProperties(targetServer, targetServerDto);
		if (SshAuthType.KEY.equals(targetServer.getSshAuthType()) && targetServer.getSshKeyId() != null) {
			SshKey sshKey = sshKeyService.getSshKey(targetServer.getSshKeyId());
			targetServerDto.setSshKeyName(sshKey.getName());
			targetServerDto.setSshUsername(sshKey.getUsername());
			targetServerDto.setSshPassword(sshKey.getPassword());
		}

		String agentStatus = agentHealthService.getHealthStatus(targetServer.getId());
		targetServerDto.setAgentStatus(agentStatus);
		return targetServerDto;
	}

}
