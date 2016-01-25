package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TargetServerAssembler extends AbstractAssembler<TargetServerCreateForm, TargetServer> {

	@Override protected TargetServer doAssemble(TargetServerCreateForm targetServerCreateForm) {
		TargetServer targetServer = new TargetServer();
		BeanUtils.copyProperties(targetServerCreateForm, targetServer);
		// TODO Password Encypt
		return targetServer;
	}

}
