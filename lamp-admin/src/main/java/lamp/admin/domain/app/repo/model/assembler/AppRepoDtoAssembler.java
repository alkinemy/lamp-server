package lamp.admin.domain.app.repo.model.assembler;


import lamp.admin.domain.app.repo.model.dto.AppRepoDto;
import lamp.admin.domain.app.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.LocalAppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.UrlAppRepoEntity;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppRepoDtoAssembler extends AbstractListAssembler<AppRepoEntity, AppRepoDto> {

	@Override protected AppRepoDto doAssemble(AppRepoEntity appRepo) {
		AppRepoDto appRepoDto = new AppRepoDto();
		BeanUtils.copyProperties(appRepo, appRepoDto);
		if (appRepo instanceof LocalAppRepoEntity) {
			appRepoDto.setEtc(((LocalAppRepoEntity) appRepo).getRepositoryPath());
		} else if (appRepo instanceof MavenAppRepoEntity) {
			appRepoDto.setEtc(((MavenAppRepoEntity) appRepo).getUrl());
		} else if (appRepo instanceof UrlAppRepoEntity) {
			appRepoDto.setEtc(((UrlAppRepoEntity) appRepo).getBaseUrl());
		}
		return appRepoDto;
	}

}
