package lamp.server.aladin.core.dto;

import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.service.AppRepoService;
import lamp.server.aladin.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppTemplateAssembler extends AbstractAssembler<AppTemplateCreateForm, AppTemplate> {

	@Autowired
	private AppRepoService appRepoService;

	@Override protected AppTemplate doAssemble(AppTemplateCreateForm appTemplateCreateForm) {
		Long repositoryId = appTemplateCreateForm.getRepositoryId();

		AppTemplate appTemplate = new AppTemplate();
		BeanUtils.copyProperties(appTemplateCreateForm, appTemplate);
		if (repositoryId != null) {
			appTemplate.setAppRepository(appRepoService.getAppRepository(repositoryId));
		}

		AppResourceType resourceType = appTemplateCreateForm.getResourceType();
		if (AppResourceType.NONE.equals(resourceType)) {
			appTemplate.setPreInstalled(true);
		} else {
			appTemplate.setPreInstalled(false);
		}

		appTemplate.setDeleted(false);
		return appTemplate;
	}
}
