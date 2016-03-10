package lamp.metrics.exporter.slf4j;

import lamp.common.metrics.MetricsExporter;
import lamp.common.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4jMetricsExporter implements MetricsExporter {

	private Logger logger;

	public Slf4jMetricsExporter(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void export(TargetMetrics targetMetrics) {
		logger.info("metrics = {}", targetMetrics);
	}


}