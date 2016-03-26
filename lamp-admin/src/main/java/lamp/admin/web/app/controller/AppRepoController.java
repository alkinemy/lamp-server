package lamp.admin.web.app.controller;

import lamp.admin.domain.app.model.AppRepoDto;
import lamp.admin.domain.app.service.AppRepoService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
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
