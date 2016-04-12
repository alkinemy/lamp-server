package lamp.admin.web.monitoring.service;

import lamp.admin.config.web.AgentProperties;
import lamp.collector.health.exporter.HealthExporter;
import lamp.common.collector.model.HealthConstants;
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
    private AgentProperties agentProperties;

    @Override
    protected void export(TargetHealth targetHealth) {
        log.error("XXXXXXXX targetHealth = {}", targetHealth);
        if (StringUtils.equals(agentProperties.getGroupId(), targetHealth.getGroupId())
                && StringUtils.equals(agentProperties.getArtifactId(), targetHealth.getArtifactId())) {
            String status = targetHealth.getHealth() != null ? (String) targetHealth.getHealth().get(HealthConstants.STATUS) : "UNKNOWN";
            agentHealthStatus.put(targetHealth.getId(), status);
        }
        log.error("agentHealthStatus = {}", agentHealthStatus);
    }
}
