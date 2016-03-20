package lamp.monitoring.core.alert.service;

import lamp.monitoring.core.alert.model.AlertActionContext;

import java.util.List;

public interface AlertActionService {

    void doActions(AlertActionContext context, List<String> actions);
}
