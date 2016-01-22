package lamp.server.aladin.admin.controller;

import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.dto.LocalAppFileDto;
import lamp.server.aladin.core.dto.LocalAppFileUploadForm;
import lamp.server.aladin.core.dto.LocalAppRepoCreateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app-repository/LOCAL")
public class LocalAppRepoController extends GenericAppRepoController<LocalAppRepoCreateForm> {

	@Override protected String getCreateViewName() {
		return "app-repository/local/edit";
	}

}
