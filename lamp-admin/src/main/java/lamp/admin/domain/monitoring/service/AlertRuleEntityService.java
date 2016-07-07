package lamp.admin.domain.monitoring.service;

import lamp.admin.domain.monitoring.model.AlertRuleEntity;
import lamp.admin.domain.monitoring.repository.AlertRuleEntityRepository;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertRuleEntityService {

	@Autowired
	private AlertRuleEntityRepository alertRuleEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public <T> List<T> getAlertRules(Class<T> clazz) {
		List<AlertRuleEntity> entities = alertRuleEntityRepository.findAllByType(clazz.getName());
		return smartAssembler.assemble(entities, AlertRuleEntity.class, clazz);
	}

}
