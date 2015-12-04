package lamp.server.aladin.domain.client.service;

import lamp.server.aladin.common.assembler.SmartAssembler;
import lamp.server.aladin.domain.client.model.LampClient;
import lamp.server.aladin.domain.client.repository.LampClientRepository;
import lamp.server.aladin.domain.client.service.command.LampClientCreateCommand;
import lamp.server.aladin.domain.client.service.command.LampClientCreateOrUpdateCommand;
import lamp.server.aladin.domain.client.service.command.LampClientDeleteCommand;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LampClientCommandService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private LampClientRepository lampClientRepository;

	@Transactional
	public void handle(LampClientCreateCommand command) {
		LampClient lampClient = smartAssembler.assemble(command, LampClient.class);
		lampClientRepository.save(lampClient);
	}

	@Transactional
	public void handle(LampClientCreateOrUpdateCommand command) {
		String id = command.getId();
		LampClient lampClient = lampClientRepository.findOne(id);
		if (lampClient == null) {
			lampClient = smartAssembler.assemble(command, LampClient.class);
			lampClientRepository.save(lampClient);
		} else {
			populateProperties(lampClient, command);
		}
	}

	@Transactional
	public void handle(LampClientDeleteCommand command) {
		lampClientRepository.delete(command.getId());
	}

	protected void populateProperties(LampClient lampClient, LampClientCreateOrUpdateCommand command) {
		BeanUtils.copyProperties(lampClient, command, LampClient.class);
	}

}
