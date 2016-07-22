package lamp.admin.domain.host.model.assembler;


import lamp.admin.core.host.Cluster;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.stereotype.Component;

@Component
public class ClusterEntityAssembler extends AbstractListAssembler<Cluster, ClusterEntity> implements Populater<Cluster, ClusterEntity> {

	@Override protected ClusterEntity doAssemble(Cluster app) {

		ClusterEntity entity = new ClusterEntity();
		entity.setId(app.getId());
		entity.setName(app.getName());
		entity.setDescription(app.getDescription());
		entity.setType(app.getType());

		entity.setDataType(app.getClass().getName());
		entity.setData(JsonUtils.stringify(app));
		return entity;
	}

	@Override public void populate(Cluster app, ClusterEntity entity) {
		entity.setName(app.getName());
		entity.setDescription(app.getDescription());

		entity.setDataType(app.getClass().getName());
		entity.setData(JsonUtils.stringify(app));
	}
}
