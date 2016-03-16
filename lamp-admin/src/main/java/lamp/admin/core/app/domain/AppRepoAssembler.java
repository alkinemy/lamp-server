package lamp.admin.core.app.domain;

import lamp.admin.core.agent.domain.UrlAppRepo;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_REPOSITORY_TYPE, repository);
		}
		BeanUtils.copyProperties(appRepoCreateForm, appRepo);
		if (StringUtils.isBlank(appRepo.getId())) {
			appRepo.setId(UUID.randomUUID().toString());
		}
		appRepo.setDeleted(false);
		return appRepo;
	}

	@Override public void populate(AppRepoUpdateForm source, AppRepo target) {
		BeanUtils.copyProperties(source, target);
	}
}
