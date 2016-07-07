package lamp.monitoring.core.alert;

import lamp.monitoring.core.alert.model.AlertActionContext;

import java.util.List;

public interface AlertActionsExecutor {

    void doActions(AlertActionContext context, List<String> actions);

}
