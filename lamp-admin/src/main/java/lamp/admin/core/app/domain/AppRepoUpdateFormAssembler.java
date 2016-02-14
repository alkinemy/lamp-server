package lamp.admin.core.app.domain;

import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppRepoUpdateFormAssembler extends AbstractAssembler<AppRepo, AppRepoUpdateForm> {

	@Override protected AppRepoUpdateForm doAssemble(AppRepo appRepo) {
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
