package lamp.admin.web.app.repo.controller;


import lamp.admin.domain.app.repo.model.form.LocalAppRepoCreateForm;
import lamp.admin.domain.app.repo.model.form.LocalAppRepoUpdateForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app/repository/LOCAL")
public class LocalAppRepoController extends GenericAppRepoController<LocalAppRepoCreateForm, LocalAppRepoUpdateForm> {

	@Override protected String getCreateViewName() {
		return "app/repository/local/edit";
	}

}
