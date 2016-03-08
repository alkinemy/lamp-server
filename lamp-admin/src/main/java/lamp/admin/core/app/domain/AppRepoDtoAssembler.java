package lamp.admin.core.app.domain;

import lamp.admin.core.agent.domain.UrlAppRepo;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppRepoDtoAssembler extends AbstractListAssembler<AppRepo, AppRepoDto> {

	@Override protected AppRepoDto doAssemble(AppRepo appRepo) {
		AppRepoDto appRepoDto = new AppRepoDto();
		BeanUtils.copyProperties(appRepo, appRepoDto);
		if (appRepo instanceof LocalAppRepo) {
			appRepoDto.setEtc(((LocalAppRepo) appRepo).getRepositoryPath());
		} else if (appRepo instanceof MavenAppRepo) {
			appRepoDto.setEtc(((MavenAppRepo) appRepo).getUrl());
		} else if (appRepo instanceof UrlAppRepo) {
			appRepoDto.setEtc(((UrlAppRepo) appRepo).getBaseUrl());
		}
		return appRepoDto;
	}

}
