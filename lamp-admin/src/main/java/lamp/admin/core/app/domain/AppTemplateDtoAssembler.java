package lamp.admin.core.app.domain;

import lamp.common.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppTemplateDtoAssembler extends AbstractAssembler<AppTemplate, AppTemplateDto> {


	@Override protected AppTemplateDto doAssemble(AppTemplate appTemplate) {
		AppTemplateDto dto = new AppTemplateDto();
		BeanUtils.copyProperties(appTemplate, dto);
		dto.setRepositoryId(appTemplate.getAppRepository().getId());
		dto.setRepositoryName(appTemplate.getAppRepository().getName());
		return dto;
	}
}
