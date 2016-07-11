package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.AlertAction;
import lamp.monitoring.core.alert.model.AlertActionContext;


public interface AlertActionExecutor<A extends AlertAction> {

    void execute(A action, AlertActionContext context);

}
