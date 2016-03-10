package lamp.collector.app.support.jpa.service;

import lamp.collector.app.support.jpa.repository.JpaCollectionTargetRepository;
import lamp.collector.core.service.CollectionTargetService;
import lamp.common.collection.CollectionTarget;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class JpaCollectionTargetService implements CollectionTargetService {

    @Autowired
    private JpaCollectionTargetRepository jpaCollectionTargetRepository;

    @Override
    public List<CollectionTarget> getCollectionTargetListForHealth() {
        return jpaCollectionTargetRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public List<CollectionTarget> getCollectionTargetListForMetrics() {
        return jpaCollectionTargetRepository.findAll().stream().collect(Collectors.toList());
    }

}
