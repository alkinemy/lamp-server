package lamp.collector.core.service;

import lamp.common.metrics.HealthTarget;

import java.util.List;

public interface HealthTargetService {

	List<HealthTarget> getHealthTargets();

}
