package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TargetServerDtoAssembler extends AbstractListAssembler<TargetServer, TargetServerDto> {

	@Override protected TargetServerDto doAssemble(TargetServer targetServer) {
		TargetServerDto targetServerDto = new TargetServerDto();
		BeanUtils.copyProperties(targetServer, targetServerDto);

		Agent agent = targetServer.getAgent();
		// TODO 상태 가져오기 구현 필요
		if (agent != null) {
			targetServerDto.setAgentStatus("Running");
		} else {
			targetServerDto.setAgentStatus("Unknown");
		}

		return targetServerDto;
	}

}
