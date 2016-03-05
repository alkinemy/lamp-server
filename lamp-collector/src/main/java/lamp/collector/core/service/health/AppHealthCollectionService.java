package lamp.collector.core.service.health;

import lamp.collector.core.domain.AppHealth;
import lamp.collector.core.domain.TargetApp;
import lamp.collector.core.service.health.collector.AppHealthCollectorService;
import lamp.collector.core.service.health.export.AppHealthExportFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppHealthCollectionService {

	@Autowired
	private AppHealthCollectorService appHealthCollectorService;

	@Autowired
	private AppHealthExportFacadeService appHealthExportFacadeService;

	public void monitoring(TargetApp app) {
		AppHealth health = appHealthCollectorService.getHealth(app);
		appHealthExportFacadeService.export(app, health);
	}

}
