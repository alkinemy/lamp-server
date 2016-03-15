package lamp.collector.core.service;

import lamp.collector.core.service.health.HealthProcessFacadeService;
import lamp.common.metrics.HealthTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class HealthProcessScheduledService {

	@Autowired
	private HealthTargetService healthTargetService;

	@Autowired
	private HealthProcessFacadeService healthProcessFacadeService;

	public void process() {
		Collection<HealthTarget> collectionTargets = healthTargetService.getHealthTargets();
		collectionTargets.forEach(healthProcessFacadeService::process);
	}

}
