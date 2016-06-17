package lamp.admin.web.app.controller;

import lamp.admin.api.util.HttpServletRequestUtils;
import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.account.model.MenuItem;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAppController {

	protected AppService appService;

	public AbstractAppController(AppService appService) {
		this.appService = appService;
	}


	@ModelAttribute("path")
	protected String getPath(HttpServletRequest request) {
		String path = HttpServletRequestUtils.getRestPath(request);
		return path;
	}


	public List<MenuItem> getBreadcrumb(App currentApp) {
		List<MenuItem> breadcrumb = new ArrayList<>();
		List<App> apps = appService.getAppsWithParentAll(currentApp);
		for (App app : apps) {
			breadcrumb.add(MenuItem.of(MenuConstants.APP, app.getName(), "/apps/" + app.getPath()));
		}
		return breadcrumb;
	}
}
