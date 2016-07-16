package lamp.admin.domain.alert.service;


import lamp.admin.domain.alert.model.entity.AlertRuleEntity;
import lamp.admin.domain.alert.repository.AlertRuleEntityRepository;
import lamp.common.utils.assembler.SmartAssembler;
import lamp.monitoring.core.base.alert.model.AlertRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertRuleService {

	@Autowired
	private AlertRuleEntityRepository alertRuleEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public <T> List<T> getAlertRules(Class<T> clazz) {
		List<AlertRuleEntity> entities = alertRuleEntityRepository.findAllByDataType(clazz.getName());
		return (List<T>) smartAssembler.assemble(entities, AlertRuleEntity.class, AlertRule.class);
	}

	public <T> Page<T> getAlertRules(Class<T> clazz, Pageable pageable) {
		Page<AlertRuleEntity> entities = alertRuleEntityRepository.findAllByDataType(clazz.getName(), pageable);
		return (Page<T>) smartAssembler.assemble(pageable, entities, AlertRuleEntity.class, AlertRule.class);
	}

	public <T> T getAlertRule(Class<T> clazz, String id) {
		AlertRuleEntity entity = alertRuleEntityRepository.findOne(id);
		return (T) smartAssembler.assemble(entity, AlertRuleEntity.class, AlertRule.class);
	}

	@Transactional
	public <T extends AlertRule> T createAlertRule(T alertRule) {
		AlertRuleEntity entity = smartAssembler.assemble(alertRule, AlertRule.class, AlertRuleEntity.class);
		AlertRuleEntity saved = alertRuleEntityRepository.save(entity);
		return (T) smartAssembler.assemble(saved, AlertRuleEntity.class, AlertRule.class);
	}

	@Transactional
	public <T extends AlertRule> T updateAlertRule(String id, T alertRule) {
		AlertRuleEntity entity = alertRuleEntityRepository.findOne(id);
		smartAssembler.populate(alertRule, entity, AlertRule.class, AlertRuleEntity.class);
		return (T) smartAssembler.assemble(entity, AlertRuleEntity.class, AlertRule.class);
	}

	@Transactional
	public void deleteAlertRule(String id) {
		alertRuleEntityRepository.delete(id);
	}
}
