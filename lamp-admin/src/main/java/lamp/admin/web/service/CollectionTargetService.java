package lamp.admin.web.service;

import lamp.admin.domain.agent.service.TargetServerService;
import lamp.collector.core.service.HealthTargetService;
import lamp.collector.core.service.MetricsTargetService;
import lamp.common.collector.model.HealthTarget;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.utils.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CollectionTargetService implements HealthTargetService, MetricsTargetService {

    @Autowired
    private TargetServerService targetServerService;

    @Override
    public List<HealthTarget> getHealthTargets() {
        return targetServerService.getTargetServerList().stream().filter(s -> BooleanUtils.isTrue(s.isHealthCollectionEnabled())).collect(Collectors.toList());
    }

    @Override
    public List<MetricsTarget> getMetricsTargets() {
        return targetServerService.getTargetServerList().stream().filter(s -> BooleanUtils.isTrue(s.isMetricsCollectionEnabled())).collect(Collectors.toList());
    }

}
