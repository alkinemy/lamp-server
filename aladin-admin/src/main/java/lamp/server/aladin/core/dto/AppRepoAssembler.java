package lamp.server.aladin.core.dto;

import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.core.domain.*;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppRepoAssembler extends AbstractAssembler<AppRepoCreateForm, AppRepo> {

	@Override protected AppRepo doAssemble(AppRepoCreateForm appRepoCreateForm) {
		AppRepo appRepo;
		AppFileType repository = appRepoCreateForm.getRepositoryType();
		if (AppFileType.LOCAL.equals(repository)) {
			appRepo = new LocalAppRepo();
		} else if (AppFileType.MAVEN.equals(repository)) {
			appRepo = new MavenAppRepo();
		} else if (AppFileType.URL.equals(repository)) {
			appRepo = new UrlAppRepo();
		} else {
			throw Exceptions.newException(AdminErrorCode.INVALID_APP_REPOSITORY_TYPE, repository);
		}
		BeanUtils.copyProperties(appRepoCreateForm, appRepo);
		appRepo.setDeleted(false);
		return appRepo;
	}
}
