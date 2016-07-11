package lamp.admin.domain.alert.model.assembler;

import lamp.admin.domain.alert.model.entity.AlertRuleEntity;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.monitoring.core.alert.model.AlertRule;
import org.springframework.stereotype.Component;

@Component
public class AlertRuleAssembler extends AbstractListAssembler<AlertRuleEntity, AlertRule> {

	@Override protected AlertRule doAssemble(AlertRuleEntity entity) {
		try {
			String className = entity.getDataType();
			Class<? extends AlertRule> clazz = (Class<? extends AlertRule>) Class.forName(className);
			AlertRule alertRule = JsonUtils.parse(entity.getData(), clazz);
			return alertRule;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}
	}

}
