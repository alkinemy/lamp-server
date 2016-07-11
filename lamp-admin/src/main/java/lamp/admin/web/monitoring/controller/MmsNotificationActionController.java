package lamp.admin.web.monitoring.controller;

import lamp.admin.domain.alert.model.SmsNotificationAction;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@MenuMapping(MenuConstants.MONITORING_ALERT_ACTIONS)
@Controller
@RequestMapping(path = "/monitoring/alert-actions", params = "type=mms")
public class MmsNotificationActionController extends GenericAlertActionController<SmsNotificationAction> {

	@Override protected String getType() {
		return "mms";
	}

}
