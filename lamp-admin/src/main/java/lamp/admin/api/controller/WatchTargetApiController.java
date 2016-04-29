package lamp.admin.api.controller;

import lamp.admin.domain.agent.service.AgentEventService;
import lamp.admin.domain.monitoring.model.WatchTargetDto;
import lamp.admin.domain.monitoring.service.WatchTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/watch-target")
public class WatchTargetApiController {

	@Autowired
	private WatchTargetService watchTargetService;

	@Autowired
	private AgentEventService agentEventService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<WatchTargetDto> list() {
		return watchTargetService.getWatchTargetDtoList();
	}

}
