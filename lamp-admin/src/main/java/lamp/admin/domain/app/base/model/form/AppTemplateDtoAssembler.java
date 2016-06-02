package lamp.admin.domain.app.base.model.form;

import lamp.admin.domain.old.app.model.AppTemplate;
import lamp.admin.domain.old.app.model.AppTemplateDto;
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
