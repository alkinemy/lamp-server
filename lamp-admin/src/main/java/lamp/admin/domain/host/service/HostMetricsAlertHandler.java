package lamp.admin.domain.host.service;

import lamp.admin.domain.monitoring.service.AlertEventService;
import lamp.admin.web.monitoring.service.HostAlertRuleService;
import lamp.monitoring.core.alert.AlertRuleProcessor;
import lamp.monitoring.metrics.MonitoringTargetMetrics;
import lamp.monitoring.metrics.processor.MonitoringTargetMetricsAlertHandler;
import lamp.monitoring.metrics.processor.MonitoringTargetMetricsHandler;
import lamp.monitoring.metrics.rule.spel.SpelTargetMetricsAlertRuleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class HostMetricsAlertHandler implements MonitoringTargetMetricsHandler {

    @Autowired
    private AlertEventService alertEventService;

    @Autowired
    private HostAlertRuleService hostAlertRuleService;

    private MonitoringTargetMetricsAlertHandler monitoringTargetMetricsAlertProcessor;
    private List<AlertRuleProcessor> alertRuleProcessors;

    @PostConstruct
    public void init() {
        alertRuleProcessors = new ArrayList<>();
        alertRuleProcessors.add(new SpelTargetMetricsAlertRuleProcessor());

        monitoringTargetMetricsAlertProcessor = new MonitoringTargetMetricsAlertHandler(hostAlertRuleService, alertRuleProcessors, alertEventService);
    }

    @Override
    public void handle(MonitoringTargetMetrics targetMetrics) {
        this.monitoringTargetMetricsAlertProcessor.handle(targetMetrics);
    }
}
