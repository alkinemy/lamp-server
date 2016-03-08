package lamp.collector.app.core.service.health;

import lamp.collector.common.CollectionTarget;
import lamp.collector.common.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HealthCollectionService {

	@Autowired
	private HealthLoaderService healthCollectorService;

	@Autowired
	private HealthExportService healthExportFacadeService;

	public void collection(CollectionTarget collectionTarget) {
		TargetHealth health = healthCollectorService.getHealth(collectionTarget);
		if (health != null) {
			healthExportFacadeService.export(health);
		}
	}

}
