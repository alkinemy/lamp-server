package lamp.collector.core.service;


import lamp.collector.core.domain.TargetApp;
import lamp.collector.core.repository.TargetAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TargetAppService {

	@Autowired
	private TargetAppRepository targetAppRepository;

	public List<TargetApp> getWatchedAppList() {
		return targetAppRepository.findAll();
	}

	public List<TargetApp> getAppListForHealthCollection() {
		return getWatchedAppList();
	}

	public List<TargetApp> getWatchedAppListForMetricsCollection() {
		return getWatchedAppList();
	}

	public Optional<TargetApp> getWatchedAppOptional(String id) {
		return Optional.ofNullable(targetAppRepository.findOne(id));
	}

}
