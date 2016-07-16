package lamp.monitoring.core.base.alert;

import lamp.monitoring.core.base.alert.model.AlertActionContext;

import java.util.List;

public interface AlertActionsExecutor {

    void doActions(AlertActionContext context, List<String> actions);

}
