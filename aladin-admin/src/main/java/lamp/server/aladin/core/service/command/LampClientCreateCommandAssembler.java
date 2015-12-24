package lamp.server.aladin.core.service.command;


import lamp.server.aladin.common.assembler.AbstractListAssembler;
import lamp.server.aladin.core.domain.Agent;
import org.springframework.beans.BeanUtils;

public class LampClientCreateCommandAssembler extends AbstractListAssembler<LampClientCreateCommand, Agent> {

	@Override protected Agent doAssemble(LampClientCreateCommand lampClientCreateCommand) {
		Agent entity = new Agent();
		BeanUtils.copyProperties(lampClientCreateCommand, entity, Agent.class);
		return entity;
	}

}
