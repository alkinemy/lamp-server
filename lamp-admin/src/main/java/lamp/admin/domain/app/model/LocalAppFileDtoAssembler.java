package lamp.admin.domain.app.model;

import lamp.admin.domain.app.service.AppRepoService;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LocalAppFileDtoAssembler extends AbstractListAssembler<LocalAppFile, LocalAppFileDto> {

	@Autowired
	private AppRepoService appRepoService;

	@Override protected LocalAppFileDto doAssemble(LocalAppFile localAppFile) {
		LocalAppFileDto dto = new LocalAppFileDto();
		BeanUtils.copyProperties(localAppFile, dto);

		Optional<AppRepo> appRepoOptional = appRepoService.getAppRepoOptional(localAppFile.getRepositoryId());
		if (appRepoOptional.isPresent()) {
			dto.setRepositoryName(appRepoOptional.get().getName());
		} else {
			dto.setRepositoryName(localAppFile.getRepositoryId());
		}
		return dto;
	}

}
