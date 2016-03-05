package lamp.collector.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.TargetApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class AppHealthCollectionScheduledService {

	@Autowired
	private TargetAppService targetAppService;

	@Autowired
	private AppHealthCollectionFacadeService appHealthCollectionFacadeService;

	public void collection() {
		Collection<TargetApp> targetApps = targetAppService.getAppListForHealthCollection();
		targetApps.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::collection);
	}

	protected void collection(TargetApp app) {
		try {
			appHealthCollectionFacadeService.collection(app);
		} catch (Exception e) {
			log.warn("Health Collection Failed", e);
		}
	}

}
