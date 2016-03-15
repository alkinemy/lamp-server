package lamp.collector.core.service.health;

import lamp.common.metrics.HealthProcessor;
import lamp.common.metrics.HealthTarget;
import lamp.common.metrics.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
public class HealthProcessService {

	@Autowired
	private HealthAsyncProcessorService healthAsyncProcessorService;

	@Autowired(required = false)
	private List<HealthProcessor> healthProcessors;

	public void process(HealthTarget target, TargetHealth health, Throwable t) {
		Optional.ofNullable(healthProcessors)
			.ifPresent(s -> s.forEach(p -> healthAsyncProcessorService.process(p, target, health, t)));
	}

}
