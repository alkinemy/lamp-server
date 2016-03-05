package lamp.collector.core.service.health.export;

import lamp.collector.core.domain.TargetHealth;
import lamp.collector.core.domain.CollectionTarget;

public interface HealthExportService {

	void export(CollectionTarget collectionTarget, TargetHealth health);

}
