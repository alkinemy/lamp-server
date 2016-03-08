package lamp.admin.core.app.domain;

import lamp.admin.core.app.service.AppRepoService;
import lamp.common.utils.assembler.AbstractAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppTemplateAssembler extends AbstractAssembler<AppTemplateCreateForm, AppTemplate> implements Populater<AppTemplateUpdateForm, AppTemplate> {

	@Autowired
	private AppRepoService appRepoService;

	@Override protected AppTemplate doAssemble(AppTemplateCreateForm appTemplateCreateForm) {
		Long repositoryId = appTemplateCreateForm.getRepositoryId();

		AppTemplate appTemplate = new AppTemplate();
		BeanUtils.copyProperties(appTemplateCreateForm, appTemplate);
		if (repositoryId != null) {
			appTemplate.setAppRepository(appRepoService.getAppRepo(repositoryId));
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

	@Override public void populate(AppTemplateUpdateForm source, AppTemplate target) {
		BeanUtils.copyProperties(source, target);
	}
}
