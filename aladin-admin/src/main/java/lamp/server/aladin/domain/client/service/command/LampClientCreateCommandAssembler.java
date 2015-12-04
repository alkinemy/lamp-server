package lamp.server.aladin.domain.client.service.command;


import lamp.server.aladin.common.assembler.AbstractListAssembler;
import lamp.server.aladin.domain.client.model.LampClient;
import lamp.server.aladin.domain.client.service.command.*;
import org.springframework.beans.BeanUtils;

public class LampClientCreateCommandAssembler extends AbstractListAssembler<lamp.server.aladin.domain.client.service.command.LampClientCreateCommand, LampClient> {

	@Override protected LampClient doAssemble(lamp.server.aladin.domain.client.service.command.LampClientCreateCommand lampClientCreateCommand) {
		LampClient entity = new LampClient();
		BeanUtils.copyProperties(lampClientCreateCommand, entity, LampClient.class);
		return entity;
	}

}
