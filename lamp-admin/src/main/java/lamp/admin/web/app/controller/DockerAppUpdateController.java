package lamp.admin.web.app.controller;

import lamp.admin.domain.app.base.model.form.DockerAppUpdateForm;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.resource.repo.model.dto.AppRepoDto;
import lamp.admin.domain.resource.repo.service.AppRepoService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.APP)
@Controller
@RequestMapping(path = "/apps", params = {"action=update-docker-app"})
public class DockerAppUpdateController extends AbstractAppController {

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	public DockerAppUpdateController(AppService appService) {
		super(appService);
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET)
	public String update(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") DockerAppUpdateForm editForm) {
		DockerAppUpdateForm form = appService.getDockerAppUpdateForm(path);
		BeanUtils.copyProperties(form, editForm);
		return updateForm(model, editForm);
	}

	protected String updateForm(Model model, DockerAppUpdateForm editForm) {
		List<AppRepoDto> appRepoList = appRepoService.getAppRepoList();
		model.addAttribute("appRepositoryList", appRepoList);
		return "apps/docker-app-edit";
	}

	@RequestMapping(path = "/**", method = RequestMethod.POST)
	public String update(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") DockerAppUpdateForm editForm,
						 BindingResult bindingResult) {
		try {
			appService.updateApp(path, editForm);

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return updateForm(model, editForm);
		}
	}

}
