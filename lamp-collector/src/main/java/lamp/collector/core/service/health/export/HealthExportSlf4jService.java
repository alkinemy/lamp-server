package lamp.collector.core.service.health.export;

import lamp.collector.core.domain.CollectionTarget;
import lamp.collector.core.domain.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class HealthExportSlf4jService implements HealthExportService {

	private Logger logger;

	public HealthExportSlf4jService(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void export(CollectionTarget collectionTarget, TargetHealth health) {
		logger.info("health = {}", health);
	}


}