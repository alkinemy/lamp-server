package lamp.server.watch.core.service;

import lamp.server.watch.core.domain.HealthStatus;
import lamp.server.watch.core.domain.WatchedApp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppStatusService {

	public void updateStatus(WatchedApp watchedApp, HealthStatus healthStatus, LocalDateTime healthStatusTime) {

	}
}
