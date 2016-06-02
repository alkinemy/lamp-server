package lamp.admin.domain.app.repo.model.assembler;


import lamp.admin.domain.app.repo.model.AppResourceType;

import lamp.admin.domain.app.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.LocalAppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.UrlAppRepoEntity;
import lamp.admin.domain.app.repo.service.dto.AppRepoCreateForm;
import lamp.admin.domain.app.repo.service.dto.AppRepoUpdateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;

import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppRepoAssembler extends AbstractAssembler<AppRepoCreateForm, AppRepoEntity> implements Populater<AppRepoUpdateForm, AppRepoEntity> {

	@Override protected AppRepoEntity doAssemble(AppRepoCreateForm appRepoCreateForm) {
		AppRepoEntity appRepo;
		AppResourceType repository = appRepoCreateForm.getRepositoryType();
		if (AppResourceType.LOCAL.equals(repository)) {
			appRepo = new LocalAppRepoEntity();
		} else if (AppResourceType.MAVEN.equals(repository)) {
			appRepo = new MavenAppRepoEntity();
		} else if (AppResourceType.URL.equals(repository)) {
			appRepo = new UrlAppRepoEntity();
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_REPOSITORY_TYPE, repository);
		}
		BeanUtils.copyProperties(appRepoCreateForm, appRepo);
		if (StringUtils.isBlank(appRepo.getId())) {
			appRepo.setId(UUID.randomUUID().toString());
		}
		appRepo.setDeleted(false);
		return appRepo;
	}

	@Override public void populate(AppRepoUpdateForm source, AppRepoEntity target) {
		BeanUtils.copyProperties(source, target);
	}
}
