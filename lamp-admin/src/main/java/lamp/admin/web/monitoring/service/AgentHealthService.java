package lamp.admin.web.monitoring.service;

import lamp.admin.domain.host.model.AgentInstallProperties;
import lamp.admin.domain.monitoring.model.HealthStatusCode;
import lamp.collector.health.exporter.HealthExporter;
import lamp.common.collector.model.HealthConstants;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class AgentHealthService extends HealthExporter {

    private ConcurrentMap<String, String> agentHealthStatus = new ConcurrentHashMap<>();

    @Autowired
    private AgentInstallProperties agentInstallProperties;


    @Override
    public boolean canProcess(HealthTarget healthTarget) {
        return StringUtils.equals(agentInstallProperties.getGroupId(), healthTarget.getGroupId())
                && StringUtils.equals(agentInstallProperties.getArtifactId(), healthTarget.getArtifactId());
    }


    @Override
    protected void export(TargetHealth targetHealth) {
        log.debug("targetHealth = {}", targetHealth);

        String status = targetHealth.getHealth() != null ? (String) targetHealth.getHealth().get(HealthConstants.STATUS) : HealthStatusCode.UNKNOWN.name();
        agentHealthStatus.put(targetHealth.getId(), status);
    }

    public String getHealthStatus(String id) {
        String status = agentHealthStatus.get(id);
        if (StringUtils.isBlank(status)) {
            return HealthStatusCode.UNKNOWN.name();
        } else {
            return status;
        }
    }
}
