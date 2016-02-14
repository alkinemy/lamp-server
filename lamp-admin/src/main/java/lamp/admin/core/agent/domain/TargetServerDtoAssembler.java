package lamp.admin.core.agent.domain;

import lamp.admin.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TargetServerDtoAssembler extends AbstractListAssembler<TargetServer, TargetServerDto> {

	@Override protected TargetServerDto doAssemble(TargetServer targetServer) {
		TargetServerDto targetServerDto = new TargetServerDto();
		BeanUtils.copyProperties(targetServer, targetServerDto);
		return targetServerDto;
	}

}
