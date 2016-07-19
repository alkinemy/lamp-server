package lamp.collector.core.metrics.processor;

import lamp.collector.core.metrics.MetricsTarget;
import lamp.collector.core.metrics.MetricsTargetProvider;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.loader.TargetMetricsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MetricsTargetCollectionProcessor {

    @Autowired
    private MetricsTargetProvider metricsTargetProvider;
    @Autowired
    private TargetMetricsLoader targetMetricsLoader;
    @Autowired
    private TargetMetricsProcessor targetMetricsProcessor;

    public void process() {
        log.debug("MetricsTargetCollection process");
        List<MetricsTarget> targets = metricsTargetProvider.getMetricsTargets();
        for (MetricsTarget target : targets) {
            log.debug("MetricsTargetCollection target = {}", target);
            TargetMetrics targetMetrics = targetMetricsLoader.getMetrics(target);
            targetMetricsProcessor.process(targetMetrics);
        }
    }

}
