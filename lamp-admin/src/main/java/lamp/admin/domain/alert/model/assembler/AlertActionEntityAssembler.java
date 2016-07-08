package lamp.admin.domain.alert.model.assembler;

import lamp.admin.domain.alert.model.entity.AlertActionEntity;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import lamp.monitoring.core.alert.model.AlertAction;
import org.springframework.stereotype.Component;

@Component
public class AlertActionEntityAssembler extends AbstractListAssembler<AlertAction, AlertActionEntity> implements Populater<AlertAction, AlertActionEntity> {

	@Override protected AlertActionEntity doAssemble(AlertAction alertAction) {
		try {
			AlertActionEntity entity = new AlertActionEntity();
			entity.setId(alertAction.getId());
			entity.setName(alertAction.getName());
			entity.setType(alertAction.getClass().getName());
			entity.setData(JsonUtils.stringify(alertAction));
			return entity;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}
	}

	@Override public void populate(AlertAction source, AlertActionEntity target) {
		target.setType(source.getClass().getName());
		target.setData(JsonUtils.stringify(source));
	}

}
