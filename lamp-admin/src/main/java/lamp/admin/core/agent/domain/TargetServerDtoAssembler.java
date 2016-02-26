package lamp.admin.core.agent.domain;

import lamp.admin.core.agent.service.SshKeyService;
import lamp.admin.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TargetServerDtoAssembler extends AbstractListAssembler<TargetServer, TargetServerDto> {

	@Autowired
	private SshKeyService sshKeyService;

	@Override protected TargetServerDto doAssemble(TargetServer targetServer) {
		TargetServerDto targetServerDto = new TargetServerDto();
		BeanUtils.copyProperties(targetServer, targetServerDto);
		if (SshAuthType.KEY.equals(targetServer.getAuthType()) && targetServer.getSshKeyId() != null) {
			SshKey sshKey = sshKeyService.getSshKey(targetServer.getSshKeyId());
			targetServerDto.setSshKeyName(sshKey.getName());
			targetServerDto.setUsername(sshKey.getUsername());
			targetServerDto.setPassword(sshKey.getPassword());
		}
		return targetServerDto;
	}

}
