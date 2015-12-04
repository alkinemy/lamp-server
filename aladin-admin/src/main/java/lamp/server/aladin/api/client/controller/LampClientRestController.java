package lamp.server.aladin.api.client.controller;

import lamp.server.aladin.domain.client.service.LampClientCommandService;
import lamp.server.aladin.domain.client.service.command.LampClientCreateOrUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/lamp/clients")
@RestController
public class LampClientRestController {

	@Autowired
	private LampClientCommandService lampClientCommandService;

	@RequestMapping(method = RequestMethod.POST)
	public void register(@Valid @RequestBody LampClientCreateOrUpdateCommand command) {
		lampClientCommandService.handle(command);
	}


}
