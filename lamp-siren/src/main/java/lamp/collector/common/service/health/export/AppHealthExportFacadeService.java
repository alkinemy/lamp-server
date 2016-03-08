package lamp.collector.common.service.health.export;

import lamp.collector.common.domain.*;
import lamp.collector.common.service.AppEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AppHealthExportFacadeService {

	@Autowired
	private AppEventService appEventService;

	@Autowired(required = false)
	private List<AppHealthExportService> appHealthExportServices;

	public void export(WatchedApp app, AppHealth health) {
		if (appHealthExportServices != null) {
			for (AppHealthExportService appHealthExportService : appHealthExportServices) {
				try {
					appHealthExportService.export(app, health);
				} catch(Throwable e) {
					log.warn("Health Export failed", e);

					AppEvent appEvent = AppEvent.of(app, AppEventName.HEALTH_EXPORT_FAILED, AppEventLevel.WARN, e);
					appEventService.publish(appEvent);
				}
			}
		}
	}

}
