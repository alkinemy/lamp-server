package lamp.admin.domain.host.model.assembler;


import lamp.admin.core.host.Cluster;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.stereotype.Component;

@Component
public class ClusterEntityAssembler extends AbstractListAssembler<Cluster, ClusterEntity> implements Populater<Cluster, ClusterEntity> {

	@Override protected ClusterEntity doAssemble(Cluster cluster) {

		ClusterEntity entity = new ClusterEntity();
		entity.setId(cluster.getId());
		entity.setName(cluster.getName());
		entity.setDescription(cluster.getDescription());
		entity.setType(cluster.getType());

		entity.setDataType(cluster.getClass().getName());
		entity.setData(JsonUtils.stringify(cluster));
		return entity;
	}

	@Override public void populate(Cluster cluster, ClusterEntity entity) {
		entity.setName(cluster.getName());
		entity.setDescription(cluster.getDescription());

		entity.setDataType(cluster.getClass().getName());
		entity.setData(JsonUtils.stringify(cluster));
	}
}
