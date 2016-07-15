package lamp.collector.app.service;

import lamp.collector.app.repository.CollectionTargetRepository;
import lamp.collector.service.HealthTargetService;
import lamp.collector.service.MetricsTargetService;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class CollectionTargetService implements HealthTargetService, MetricsTargetService {

    @Autowired
    private CollectionTargetRepository collectionTargetRepository;

    @Override
    public List<HealthTarget> getHealthTargets() {
        return collectionTargetRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<MetricsTarget> getMetricsTargets() {
        return collectionTargetRepository.findAll().stream().collect(Collectors.toList());
    }

}
