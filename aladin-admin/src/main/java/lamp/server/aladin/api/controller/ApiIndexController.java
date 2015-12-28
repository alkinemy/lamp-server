package lamp.server.aladin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiIndexController {

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(@AuthenticationPrincipal User user) {
		log.error("XXXX {}", user);
		return "index";
	}

}
