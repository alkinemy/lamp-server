package lamp.server.aladin.admin.controller;

import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.dto.MavenAppRepoCreateForm;
import lamp.server.aladin.core.dto.UrlAppRepoCreateForm;
import lamp.server.aladin.core.dto.UrlAppRepoUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app/repository/URL")
public class UrlAppRepoController extends GenericAppRepoController<UrlAppRepoCreateForm, UrlAppRepoUpdateForm> {

	@Override protected String getCreateViewName() {
		return "app/repository/url/edit";
	}
}
