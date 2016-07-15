package lamp.collector.service;

import lamp.collector.service.health.HealthProcessService;
import lamp.common.collector.model.HealthTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class HealthProcessScheduledService {

	@Autowired
	private HealthTargetService healthTargetService;

	@Autowired
	private HealthProcessService healthProcessService;

	public void process() {
		Collection<HealthTarget> collectionTargets = healthTargetService.getHealthTargets();
		collectionTargets.forEach(healthProcessService::process);
	}

}
