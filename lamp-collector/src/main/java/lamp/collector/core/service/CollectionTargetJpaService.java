package lamp.collector.core.service;

import lamp.collector.core.domain.CollectionTarget;
import lamp.collector.core.repository.CollectionTargetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CollectionTargetJpaService implements CollectionTargetService {

    @Autowired
    private CollectionTargetRepository collectionTargetRepository;

    @Override
    public List<CollectionTarget> getCollectionTargetListForHealth() {
        return collectionTargetRepository.findAll();
    }

    @Override
    public List<CollectionTarget> getCollectionTargetListForMetrics() {
        return collectionTargetRepository.findAll();
    }

}
