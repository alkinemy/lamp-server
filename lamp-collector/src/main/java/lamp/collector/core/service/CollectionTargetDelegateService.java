package lamp.collector.core.service;


import lamp.collector.core.domain.CollectionTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CollectionTargetDelegateService {

	@Autowired
	private CollectionTargetService collectionTargetService;

	public List<CollectionTarget> getCollectionTargetListForHealth() {
		return collectionTargetService.getCollectionTargetListForHealth();
	}

	public List<CollectionTarget> getCollectionTargetListForMetrics() {
		return collectionTargetService.getCollectionTargetListForMetrics();
	}

}
