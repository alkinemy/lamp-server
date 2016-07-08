package lamp.admin.domain.alert.service;

import lamp.admin.domain.alert.model.entity.AlertActionEntity;
import lamp.admin.domain.alert.repository.AlertActionEntityRepository;
import lamp.common.utils.assembler.SmartAssembler;
import lamp.monitoring.core.alert.model.AlertAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertActionService {

	@Autowired
	private AlertActionEntityRepository alertActionEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public AlertAction getAlertAction(String id) {
		AlertActionEntity alertActionEntity = alertActionEntityRepository.findOne(id);
		return smartAssembler.assemble(alertActionEntity, AlertActionEntity.class, AlertAction.class);
	}

	@Transactional
	public AlertAction createAlertAction(AlertAction alertAction) {
		AlertActionEntity alertActionEntity = smartAssembler.assemble(alertAction, AlertAction.class, AlertActionEntity.class);
		AlertActionEntity saved = alertActionEntityRepository.save(alertActionEntity);
		return smartAssembler.assemble(saved, AlertActionEntity.class, AlertAction.class);
	}

	@Transactional
	public AlertAction updateAlertAction(AlertAction alertAction) {
		AlertActionEntity alertActionEntity = alertActionEntityRepository.findOne(alertAction.getId());
		smartAssembler.populate(alertAction, alertActionEntity);
		return smartAssembler.assemble(alertActionEntity, AlertActionEntity.class, AlertAction.class);
	}

	@Transactional
	public void deleteAlertAction(AlertAction alertAction) {
		alertActionEntityRepository.delete(alertAction.getId());
	}

}
