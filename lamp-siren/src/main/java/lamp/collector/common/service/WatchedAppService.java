package lamp.collector.common.service;


import lamp.collector.common.domain.WatchedApp;
import lamp.collector.common.repository.WatchedAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WatchedAppService {

	@Autowired
	private WatchedAppRepository watchedAppRepository;

	public List<WatchedApp> getWatchedAppList() {
		return watchedAppRepository.findAll();
	}

	public List<WatchedApp> getWatchedAppListForHealthCollection() {
		return getWatchedAppList();
	}

	public List<WatchedApp> getWatchedAppListForMetricsCollection() {
		return getWatchedAppList();
	}

	public Optional<WatchedApp> getWatchedAppOptional(String id) {
		return Optional.ofNullable(watchedAppRepository.findOne(id));
	}

}
