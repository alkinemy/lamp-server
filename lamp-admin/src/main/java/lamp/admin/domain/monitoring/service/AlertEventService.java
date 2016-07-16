package lamp.admin.domain.monitoring.service;


import lamp.monitoring.core.base.alert.AlertActionsExecutor;
import lamp.monitoring.core.base.alert.AlertEventProcessor;
import lamp.monitoring.core.base.alert.AlertEventProducer;
import lamp.monitoring.core.base.alert.AlertStore;
import lamp.monitoring.core.base.alert.model.AlertEvent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertEventService extends AlertEventProcessor implements AlertEventProducer {

	@Autowired
	public AlertEventService(AlertStore alertStore,
							 AlertActionsExecutor alertActionsExecutor) {
		super(alertStore, alertActionsExecutor);
	}

	@Override public void send(AlertEvent alertEvent) {
		log.error("alertEvent = {}", alertEvent);
		process(alertEvent);
	}

}
