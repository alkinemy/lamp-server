package lamp.collector.core.service.health;

import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HealthProcessService {

	@Autowired
	private HealthLoaderService healthLoaderService;

	@Autowired
	private HealthProcessorService healthProcessorService;

	public void process(HealthTarget target) {
		if (!target.isHealthCollectionEnabled()) {
			return;
		}

		TargetHealth health = null;
		Throwable throwable = null;
		try {
			health = healthLoaderService.getHealth(target);
		} catch(Throwable e) {
			throwable = e;
		}
		healthProcessorService.process(target, health, throwable);
	}

}
