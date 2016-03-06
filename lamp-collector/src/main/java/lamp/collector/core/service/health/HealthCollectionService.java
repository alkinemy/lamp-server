package lamp.collector.core.service.health;

import lamp.collector.core.domain.TargetHealth;
import lamp.collector.core.domain.CollectionTarget;
import lamp.collector.core.service.health.collector.HealthCollectorService;
import lamp.collector.core.service.health.export.HealthExportFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HealthCollectionService {

	@Autowired
	private HealthCollectorService healthCollectorService;

	@Autowired
	private HealthExportFacadeService healthExportFacadeService;

	public void collection(CollectionTarget app) {
		TargetHealth health = healthCollectorService.getHealth(app);
		healthExportFacadeService.export(app, health);
	}

}
