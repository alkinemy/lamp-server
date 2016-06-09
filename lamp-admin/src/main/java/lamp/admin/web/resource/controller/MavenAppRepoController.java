package lamp.admin.web.resource.controller;


import lamp.admin.domain.resource.repo.model.form.MavenAppRepoCreateForm;
import lamp.admin.domain.resource.repo.model.form.MavenAppRepoUpdateForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/resource/repository/MAVEN")
public class MavenAppRepoController extends GenericAppRepoController<MavenAppRepoCreateForm, MavenAppRepoUpdateForm> {

	@Override protected String getCreateViewName() {
		return "resource/repository/maven/edit";
	}

}
