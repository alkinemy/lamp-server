package lamp.admin.domain.app.base.model.assembler;

import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class AppAssembler extends AbstractListAssembler <AppEntity, App> {

	@Override protected App doAssemble(AppEntity entity) {
		App app = JsonUtils.parse(entity.getData(), App.class);
		app.setId(entity.getId());
		return app;
	}

}
