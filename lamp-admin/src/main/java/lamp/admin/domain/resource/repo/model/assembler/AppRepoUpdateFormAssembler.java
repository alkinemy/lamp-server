package lamp.admin.domain.resource.repo.model.assembler;

import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.resource.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.resource.repo.model.form.AppRepoUpdateForm;
import lamp.admin.domain.resource.repo.model.form.LocalAppRepoUpdateForm;
import lamp.admin.domain.resource.repo.model.form.MavenAppRepoUpdateForm;
import lamp.admin.domain.resource.repo.model.form.UrlAppRepoUpdateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppRepoUpdateFormAssembler extends AbstractAssembler<AppRepoEntity, AppRepoUpdateForm> {

	@Override protected AppRepoUpdateForm doAssemble(AppRepoEntity appRepo) {
		AppRepoUpdateForm updateForm;
		AppResourceType repository = appRepo.getRepositoryType();
		if (AppResourceType.LOCAL.equals(repository)) {
			updateForm = new LocalAppRepoUpdateForm();
		} else if (AppResourceType.MAVEN.equals(repository)) {
			updateForm = new MavenAppRepoUpdateForm();
		} else if (AppResourceType.URL.equals(repository)) {
			updateForm = new UrlAppRepoUpdateForm();
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_REPOSITORY_TYPE, repository);
		}
		BeanUtils.copyProperties(appRepo, updateForm);
		return updateForm;
	}

}
