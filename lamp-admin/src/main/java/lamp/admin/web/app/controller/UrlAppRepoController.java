package lamp.admin.web.app.controller;

import lamp.admin.domain.app.model.UrlAppRepoCreateForm;
import lamp.admin.domain.app.model.UrlAppRepoUpdateForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
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
