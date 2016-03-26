package lamp.admin.web.app.controller;

import lamp.admin.domain.app.model.MavenAppRepoCreateForm;
import lamp.admin.domain.app.model.MavenAppRepoUpdateForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app/repository/MAVEN")
public class MavenAppRepoController extends GenericAppRepoController<MavenAppRepoCreateForm, MavenAppRepoUpdateForm> {

	@Override protected String getCreateViewName() {
		return "app/repository/maven/edit";
	}

}
