package lamp.watch.server.core.service;


import lamp.admin.utils.assembler.SmartAssembler;
import lamp.watch.server.core.domain.AppEvent;
import lamp.watch.server.core.domain.AppEventForm;
import lamp.watch.server.core.domain.AppMetrics;
import lamp.watch.server.core.domain.WatchedApp;
import lamp.watch.server.core.repository.AppEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class WatchedAppService {


	public Optional<WatchedApp> getWatchedAppOptional(String id) {
		return null;
	}

	public WatchedApp createWatchedApp(AppMetrics appMetrics) {
		return null;
	}
}
