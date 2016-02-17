package lamp.admin.web.controller;

import lamp.admin.core.app.domain.AppInstallExecuteCommandCreateForm;
import lamp.admin.core.app.domain.AppInstallExecuteCommandUpdateForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@MenuMapping(MenuConstants.APP_TEMPLATE)
@Controller
@RequestMapping(value = "/app/template/{templateId}/script/{scriptId}/command/EXECUTE")
public class AppInstallExecuteCommandController extends GenericAppInstallCommandController<AppInstallExecuteCommandCreateForm, AppInstallExecuteCommandUpdateForm> {

	@Override protected String getCreateViewName() {
		return "/app/template/script/command/execute";
	}
}
