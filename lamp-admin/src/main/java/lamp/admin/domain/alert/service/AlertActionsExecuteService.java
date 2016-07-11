package lamp.admin.domain.alert.service;


import lamp.admin.domain.alert.MmsNotificationActionExecutor;
import lamp.admin.domain.alert.SmsNotificationActionExecutor;
import lamp.admin.domain.alert.model.MmsNotificationAction;
import lamp.admin.domain.alert.model.SmsNotificationAction;
import lamp.monitoring.core.alert.AlertActionsExecutor;
import lamp.monitoring.core.alert.model.AlertAction;
import lamp.monitoring.core.alert.model.AlertActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlertActionsExecuteService implements AlertActionsExecutor {

	@Autowired(required = false)
	private SmsNotificationActionExecutor smsNotificationActionExecutor;

	@Autowired(required = false)
	private MmsNotificationActionExecutor mmsNotificationActionExecutor;

	@Autowired
	private AlertActionService alertActionService;

	@Override public void doActions(AlertActionContext context, List<String> actions) {
		for (String actionId : actions) {
			try {
				doAction(context, actionId);
			} catch (Exception e) {
				log.warn("alert action failed", e);
			}
		}
	}

	public void doAction(AlertActionContext context, String actionId) throws Exception {
		AlertAction alertAction = alertActionService.getAlertAction(actionId);
		doAction(context, alertAction);
	}

	public void doAction(AlertActionContext context, AlertAction action) throws Exception {
		if (action instanceof MmsNotificationAction) {
			mmsNotificationActionExecutor.execute((MmsNotificationAction) action, context);
		} else if (action instanceof SmsNotificationAction) {
			smsNotificationActionExecutor.execute((SmsNotificationAction) action, context);
		} else {
			log.error("Unsupported AlertAction : {}", action);
		}

	}

}
