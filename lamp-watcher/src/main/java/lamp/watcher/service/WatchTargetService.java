package lamp.watcher.service;

import lamp.collector.core.service.HealthTargetService;
import lamp.collector.core.service.MetricsTargetService;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lamp.watcher.repository.WatchTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchTargetService implements HealthTargetService, MetricsTargetService {

    @Autowired
    private WatchTargetRepository watchTargetRepository;

    @Override
    public List<HealthTarget> getHealthTargets() {
        return watchTargetRepository.findAll().stream().filter(t -> t.isHealthCollectionEnabled()).collect(Collectors.toList());
    }

    @Override
    public List<MetricsTarget> getMetricsTargets() {
        return watchTargetRepository.findAll().stream().filter(t -> t.isMetricsCollectionEnabled()).collect(Collectors.toList());
    }

}
