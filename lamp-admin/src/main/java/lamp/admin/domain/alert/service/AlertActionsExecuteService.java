package lamp.admin.domain.alert.service;


import lamp.admin.domain.alert.MmsNotificationActionExecutor;
import lamp.admin.domain.alert.model.MmsNotificationAction;
import lamp.monitoring.core.alert.AlertActionsExecutor;
import lamp.monitoring.core.alert.model.AlertActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlertActionsExecuteService implements AlertActionsExecutor {

	@Autowired(required = false)
	private MmsNotificationActionExecutor mmsNotificationActionExecutor;

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
		MmsNotificationAction action = new MmsNotificationAction();
		action.setSubject("Host Monitoring Alert");
		action.setMessage("[#{target.tags.clusterId}] #{rule.name} #{target.tags.hostName} #{state.value}");
		action.setPhoneNumbers("010-2768-0229");
		doAction(context, action);
	}

	public void doAction(AlertActionContext context, MmsNotificationAction action) throws Exception {
		mmsNotificationActionExecutor.execute(context, action);
	}

}
