package lamp.admin.domain.alert.service;

import lamp.admin.domain.alert.model.entity.AlertActionEntity;
import lamp.admin.domain.alert.repository.AlertActionEntityRepository;
import lamp.common.utils.assembler.SmartAssembler;
import lamp.monitoring.core.base.alert.model.AlertAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertActionService {

	@Autowired
	private AlertActionEntityRepository alertActionEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;
	private List<AlertAction> pubilcAlertActions;

	public List<AlertAction> getPubilcAlertActions() {
		List<AlertActionEntity> entities = alertActionEntityRepository.findAllByPrivatedFalse();
		return smartAssembler.assemble(entities, AlertActionEntity.class, AlertAction.class);
	}

	public Page<AlertAction> getPublicAlertActions(Pageable pageable) {
		Page<AlertActionEntity> entities = alertActionEntityRepository.findAllByPrivatedFalse(pageable);
		return smartAssembler.assemble(pageable, entities, AlertActionEntity.class, AlertAction.class);
	}

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
	public AlertAction updateAlertAction(String id, AlertAction alertAction) {
		AlertActionEntity alertActionEntity = alertActionEntityRepository.findOne(id);
		smartAssembler.populate(alertAction, alertActionEntity, AlertAction.class, AlertActionEntity.class);
		return smartAssembler.assemble(alertActionEntity, AlertActionEntity.class, AlertAction.class);
	}

	@Transactional
	public void deleteAlertAction(AlertAction alertAction) {
		alertActionEntityRepository.delete(alertAction.getId());
	}

	@Transactional
	public void deleteAlertAction(String id) {
		alertActionEntityRepository.delete(id);
	}


}
