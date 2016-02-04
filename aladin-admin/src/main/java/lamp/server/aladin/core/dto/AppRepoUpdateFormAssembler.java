package lamp.server.aladin.core.dto;

import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.utils.assembler.AbstractAssembler;
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
			throw Exceptions.newException(AdminErrorCode.INVALID_APP_REPOSITORY_TYPE, repository);
		}
		BeanUtils.copyProperties(appRepo, updateForm);
		return updateForm;
	}

}
