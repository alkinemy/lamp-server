package lamp.collector.common.service;


import lamp.collector.common.domain.MonitoringTarget;
import lamp.collector.common.repository.WatchedAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MonitoringTargetService {

	@Autowired
	private WatchedAppRepository watchedAppRepository;

	public List<MonitoringTarget> getWatchedAppList() {
		return watchedAppRepository.findAll();
	}

	public List<MonitoringTarget> getWatchedAppListForHealthCollection() {
		return getWatchedAppList();
	}

	public List<MonitoringTarget> getWatchedAppListForMetricsCollection() {
		return getWatchedAppList();
	}

	public Optional<MonitoringTarget> getWatchedAppOptional(String id) {
		return Optional.ofNullable(watchedAppRepository.findOne(id));
	}

}
