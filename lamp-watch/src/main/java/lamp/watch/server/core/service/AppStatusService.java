package lamp.watch.server.core.service;

import lamp.watch.server.core.domain.AppHealthStatus;
import lamp.watch.server.core.domain.WatchedApp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppStatusService {

	public void updateStatus(WatchedApp watchedApp, AppHealthStatus healthStatus, LocalDateTime healthStatusTime) {

	}
}
