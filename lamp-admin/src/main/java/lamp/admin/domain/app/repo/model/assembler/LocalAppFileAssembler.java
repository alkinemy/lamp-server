package lamp.admin.domain.app.repo.model.assembler;

import lamp.admin.domain.app.repo.model.LocalAppFileDto;
import lamp.admin.domain.app.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.LocalAppFileEntity;

import lamp.admin.domain.old.app.service.AppRepoEntityService;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocalAppFileAssembler extends AbstractListAssembler<LocalAppFileEntity, LocalAppFileDto> {

	@Autowired
	private AppRepoEntityService appRepoEntityService;

	@Override protected LocalAppFileDto doAssemble(LocalAppFileEntity localAppFile) {
		LocalAppFileDto dto = new LocalAppFileDto();
		BeanUtils.copyProperties(localAppFile, dto);

		Optional<AppRepoEntity> appRepoOptional = appRepoEntityService.getAppRepoOptional(localAppFile.getRepositoryId());
		if (appRepoOptional.isPresent()) {
			dto.setRepositoryName(appRepoOptional.get().getName());
		} else {
			dto.setRepositoryName(localAppFile.getRepositoryId());
		}
		return dto;
	}

}
