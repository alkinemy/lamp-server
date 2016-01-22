package lamp.server.aladin.admin.controller;

import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.dto.LocalAppRepoCreateForm;
import lamp.server.aladin.core.dto.MavenAppRepoCreateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app-repository/MAVEN")
public class MavenAppRepoController extends GenericAppRepoController<MavenAppRepoCreateForm> {

	@Override protected String getCreateViewName() {
		return "app-repository/maven/edit";
	}

}
