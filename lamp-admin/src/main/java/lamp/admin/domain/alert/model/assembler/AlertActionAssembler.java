package lamp.admin.domain.alert.model.assembler;

import lamp.admin.domain.alert.model.entity.AlertActionEntity;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.support.json.JsonUtils;

import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.monitoring.core.alert.model.AlertAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlertActionAssembler extends AbstractListAssembler<AlertActionEntity, AlertAction> {

	@Override protected AlertAction doAssemble(AlertActionEntity entity) {
		try {
			String className = entity.getDataType();
			Class<? extends AlertAction> clazz = (Class<? extends AlertAction>) Class.forName(className);
			AlertAction alertAction = JsonUtils.parse(entity.getData(), clazz);
			return alertAction;
		} catch (Exception e) {
			log.error("XX", e);
			throw new CannotAssembleException(e);
		}
	}

}
