package lamp.admin.domain.host.service;

import lamp.admin.core.host.AwsCluster;
import lamp.admin.core.host.Cluster;
import lamp.admin.core.host.ClusterType;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.host.repository.ClusterEntityRepository;
import lamp.admin.domain.host.service.form.ClusterForm;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ClusterService {

	@Autowired
	private ClusterEntityRepository clusterEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public List<Cluster> getClusterList() {
		List<ClusterEntity> clusterEntities = clusterEntityRepository.findAll();
		return smartAssembler.assemble(clusterEntities, ClusterEntity.class, Cluster.class);
	}

	public Cluster getCluster(String id) {
		ClusterEntity clusterEntity = getClusterEntity(id);
		return smartAssembler.assemble(clusterEntity, ClusterEntity.class, Cluster.class);
	}

	public ClusterEntity getClusterEntity(String id) {
		Optional<ClusterEntity> clusterOptional = getClusterEntityOptional(id);
		Exceptions.throwsException(!clusterOptional.isPresent(), AdminErrorCode.HOST_NOT_FOUND, id);
		return clusterOptional.get();
	}

	public Optional<Cluster> getClusterOptional(String id) {
		Optional<ClusterEntity> clusterEntityOptional = getClusterEntityOptional(id);
		Cluster cluster =  smartAssembler.assemble(clusterEntityOptional.orElse(null), ClusterEntity.class, Cluster.class);
		return Optional.ofNullable(cluster);
	}

	public Optional<ClusterEntity> getClusterEntityOptional(String id) {
		return Optional.ofNullable(clusterEntityRepository.findOne(id));
	}


	@Transactional
	public Cluster addCluster(ClusterForm editForm) {
		if (StringUtils.isBlank(editForm.getId())) {
			editForm.setId(UUID.randomUUID().toString());
		}
		if (ClusterType.AWS.equals(editForm.getType())) {
			AwsCluster cluster = new AwsCluster();
			BeanUtils.copyProperties(editForm, cluster);

			return addCluster(cluster);
		} else {
			Cluster cluster = new Cluster();
			BeanUtils.copyProperties(editForm, cluster);

			return addCluster(cluster);
		}
	}

	@Transactional
	public Cluster addCluster(Cluster cluster) {
		if (StringUtils.isBlank(cluster.getId())) {
			cluster.setId(UUID.randomUUID().toString());
		}
		ClusterEntity clusterEntity = smartAssembler.assemble(cluster, Cluster.class, ClusterEntity.class);
		ClusterEntity saved = addClusterEntity(clusterEntity);
		return smartAssembler.assemble(saved, ClusterEntity.class, Cluster.class);
	}

	@Transactional
	public ClusterEntity addClusterEntity(ClusterEntity clusterEntity) {
		return clusterEntityRepository.save(clusterEntity);
	}

}



