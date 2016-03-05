package lamp.collector.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.TargetApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class AppMetricsCollectionScheduledService {

	@Autowired
	private TargetAppService targetAppService;

	@Autowired
	private AppMetricsCollectionFacadeService appMetricsCollectionFacadeService;

	public void collection() {
		Collection<TargetApp> targetApps = targetAppService.getWatchedAppListForMetricsCollection();
		targetApps.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::metricsMonitoring);
	}

	protected void metricsMonitoring(TargetApp app) {
		try {
			appMetricsCollectionFacadeService.collection(app);
		} catch (Exception e) {
			log.warn("Metrics Collection Failed", e);
		}
	}

}
