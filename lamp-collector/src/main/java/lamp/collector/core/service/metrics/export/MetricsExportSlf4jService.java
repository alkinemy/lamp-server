package lamp.collector.core.service.metrics.export;

import lamp.collector.core.domain.CollectionTarget;
import lamp.collector.core.domain.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class MetricsExportSlf4jService implements MetricsExportService {

	private Logger logger;

	public MetricsExportSlf4jService(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void export(CollectionTarget collectionTarget, TargetMetrics metrics) {
		logger.info("metrics = {}", metrics);
	}


}