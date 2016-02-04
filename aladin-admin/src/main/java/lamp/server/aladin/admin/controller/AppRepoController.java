package lamp.server.aladin.admin.controller;

import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.dto.*;
import lamp.server.aladin.core.service.AppRepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping("/app/repository")
public class AppRepoController {

	@Autowired
	private AppRepoService appRepoService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Page<AppRepoDto> page = appRepoService.getAppRepoList(pageable);
		model.addAttribute("page", page);
		return "app/repository/list";
	}

}
