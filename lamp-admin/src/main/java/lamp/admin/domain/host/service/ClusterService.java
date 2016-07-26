package lamp.admin.domain.host.service;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.host.AwsCluster;
import lamp.admin.core.host.Cluster;
import lamp.admin.core.host.ClusterType;
import lamp.admin.core.host.HostCredentials;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.host.repository.ClusterEntityRepository;
import lamp.admin.domain.host.service.form.ClusterForm;
import lamp.admin.domain.host.service.form.HostCredentialsForm;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.FileUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
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

	public AwsCluster getAwsCluster(String id) {
		Cluster cluster = getCluster(id);
		boolean isAwsCluster = (cluster instanceof AwsCluster);
		Exceptions.throwsException(!isAwsCluster, AdminErrorCode.CLUSTER_NOT_FOUND, id);
		return (AwsCluster) cluster;
	}

	public ClusterEntity getClusterEntity(String id) {
		Optional<ClusterEntity> clusterOptional = getClusterEntityOptional(id);
		Exceptions.throwsException(!clusterOptional.isPresent(), AdminErrorCode.CLUSTER_NOT_FOUND, id);
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
		Cluster cluster = createCluster(UUID.randomUUID().toString(), editForm);
		return addCluster(cluster);
	}

	protected Cluster createCluster(String id, ClusterForm editForm) {
		Cluster cluster;
		if (ClusterType.AWS.equals(editForm.getType())) {
			cluster = new AwsCluster();
		} else {
			cluster = new Cluster();
		}
		BeanUtils.copyProperties(editForm, cluster);
		cluster.setId(id);
		return cluster;
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

	public ClusterForm getClusterForm(String id) {
		Cluster cluster = getCluster(id);
		ClusterForm clusterForm = new ClusterForm();
		BeanUtils.copyProperties(cluster, clusterForm);
		return clusterForm;
	}

	@Transactional
	public Cluster updateCluster(String id, ClusterForm editForm) {
		Cluster cluster = createCluster(id, editForm);
		return updateCluster(id, cluster);
	}

	@Transactional
	public Cluster updateCluster(String id, Cluster cluster) {
		ClusterEntity clusterEntity = getClusterEntity(id);
		smartAssembler.populate(cluster, clusterEntity, Cluster.class, ClusterEntity.class);
		return smartAssembler.assemble(clusterEntity, ClusterEntity.class, Cluster.class);
	}


}



