package lamp.collector.core.service.health;

import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class HealthProcessFacadeService {

	@Autowired
	private HealthLoadService healthLoadService;

	@Autowired
	private HealthProcessService healthProcessService;

	@Async
	public void process(HealthTarget target) {
		TargetHealth health = null;
		Throwable throwable = null;
		try {
			health = healthLoadService.getHealth(target);
		} catch(Throwable e) {
			throwable = e;
		}
		healthProcessService.process(target, health, throwable);
	}

}
