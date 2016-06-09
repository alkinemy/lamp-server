package lamp.admin.web.resource.controller;


import lamp.admin.domain.resource.repo.model.form.UrlAppRepoCreateForm;
import lamp.admin.domain.resource.repo.model.form.UrlAppRepoUpdateForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/resource/repository/URL")
public class UrlAppRepoController extends GenericAppRepoController<UrlAppRepoCreateForm, UrlAppRepoUpdateForm> {

	@Override protected String getCreateViewName() {
		return "resource/repository/url/edit";
	}
}
