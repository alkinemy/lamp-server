package lamp.server.aladin.core.dto;

import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.core.domain.*;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.utils.assembler.AbstractAssembler;
import lamp.server.aladin.utils.assembler.Populater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppRepoAssembler extends AbstractAssembler<AppRepoCreateForm, AppRepo> implements Populater<AppRepoUpdateForm, AppRepo> {

	@Override protected AppRepo doAssemble(AppRepoCreateForm appRepoCreateForm) {
		AppRepo appRepo;
		AppResourceType repository = appRepoCreateForm.getRepositoryType();
		if (AppResourceType.LOCAL.equals(repository)) {
			appRepo = new LocalAppRepo();
		} else if (AppResourceType.MAVEN.equals(repository)) {
			appRepo = new MavenAppRepo();
		} else if (AppResourceType.URL.equals(repository)) {
			appRepo = new UrlAppRepo();
		} else {
			throw Exceptions.newException(AdminErrorCode.INVALID_APP_REPOSITORY_TYPE, repository);
		}
		BeanUtils.copyProperties(appRepoCreateForm, appRepo);
		appRepo.setDeleted(false);
		return appRepo;
	}

	@Override public void populate(AppRepoUpdateForm source, AppRepo target) {
		BeanUtils.copyProperties(source, target);
	}
}
