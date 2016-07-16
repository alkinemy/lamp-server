package lamp.admin.domain.alert.model.assembler;

import lamp.admin.domain.alert.model.entity.AlertRuleEntity;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import lamp.monitoring.core.base.alert.model.AlertRule;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AlertRuleEntityAssembler extends AbstractListAssembler<AlertRule, AlertRuleEntity> implements Populater<AlertRule, AlertRuleEntity> {

	@Override protected AlertRuleEntity doAssemble(AlertRule alertRule) {
		try {
			AlertRuleEntity entity = new AlertRuleEntity();
			if (StringUtils.isEmpty(alertRule.getId())) {
				alertRule.setId(UUID.randomUUID().toString());
			}
			entity.setId(alertRule.getId());
			entity.setName(alertRule.getName());
			entity.setDescription(alertRule.getDescription());
			entity.setDataType(alertRule.getClass().getName());
			entity.setData(JsonUtils.stringify(alertRule));
			return entity;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}
	}

	@Override public void populate(AlertRule source, AlertRuleEntity target) {
		target.setName(source.getName());
		target.setDescription(source.getDescription());
		target.setDataType(source.getClass().getName());
		target.setData(JsonUtils.stringify(source));
	}

}
