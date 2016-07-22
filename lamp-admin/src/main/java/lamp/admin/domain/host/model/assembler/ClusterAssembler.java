package lamp.admin.domain.host.model.assembler;


import lamp.admin.core.host.Cluster;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClusterAssembler extends AbstractListAssembler <ClusterEntity, Cluster> {

	@Override protected Cluster doAssemble(ClusterEntity entity) {
		Cluster app = JsonUtils.parse(entity.getData(), Cluster.class);
		return app;
	}

}
