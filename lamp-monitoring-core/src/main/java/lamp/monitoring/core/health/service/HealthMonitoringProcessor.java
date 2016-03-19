package lamp.monitoring.core.health.service;

import lamp.common.collector.model.HealthConstants;
import lamp.common.collector.model.HealthStatusCode;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lamp.common.collector.service.HealthProcessor;
import lamp.monitoring.core.health.model.MonitoringHealthTarget;
import lamp.common.utils.ExceptionUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class HealthMonitoringProcessor implements HealthProcessor {

    @Override
    public boolean canProcess(HealthTarget healthTarget) {
        if (healthTarget instanceof MonitoringHealthTarget) {
            return ((MonitoringHealthTarget) healthTarget).isHealthMonitoringEnabled();
        }
        return false;
    }

    @Override
    public void process(HealthTarget healthTarget, TargetHealth targetHealth, Throwable t) {
        if (targetHealth == null) {
            targetHealth = new TargetHealth();
            targetHealth.setId(healthTarget.getId());
            targetHealth.setName(healthTarget.getName());

            Map<String, Object> health = new LinkedHashMap<>();
            health.put(HealthConstants.STATUS, HealthStatusCode.UNKNOWN.name());
            if (t != null) {
                health.put(HealthConstants.DESCRIPTION, ExceptionUtils.getStackTrace(t));
            }
            targetHealth.setHealth(health);
        }

        monitoring(targetHealth);
    }

    abstract protected void monitoring(TargetHealth targetHealth);

}
