package lamp.collector.service.health;

import lamp.common.collector.service.HealthProcessor;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
public class HealthProcessorService {

	@Autowired
	private HealthProcessorAsyncSupportService healthProcessorAsyncSupportService;

	@Autowired(required = false)
	private List<HealthProcessor> healthProcessors;

	public void process(HealthTarget target, TargetHealth health, Throwable t) {
		Optional.ofNullable(healthProcessors)
			.ifPresent(s -> s.stream()
					.filter(p -> p.canProcess(target))
					.forEach(p -> healthProcessorAsyncSupportService.process(p, target, health, t)));
	}

}
