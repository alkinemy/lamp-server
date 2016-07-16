package lamp.monitoring.core.base.alert;

import lamp.monitoring.core.base.alert.model.AlertAction;
import lamp.monitoring.core.base.alert.model.AlertActionContext;


public interface AlertActionExecutor<A extends AlertAction> {

    void execute(A action, AlertActionContext context);

}
