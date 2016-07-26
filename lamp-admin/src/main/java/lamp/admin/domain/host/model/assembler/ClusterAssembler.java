package lamp.admin.domain.host.model.assembler;


import lamp.admin.core.host.Cluster;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.monitoring.core.base.alert.model.AlertAction;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

@Component
public class ClusterAssembler extends AbstractListAssembler <ClusterEntity, Cluster> {

	@Override protected Cluster doAssemble(ClusterEntity entity) {
		try {
			String dataType = entity.getDataType();
			Class<? extends Cluster> clazz = (Class<? extends Cluster>) Class.forName(dataType);
			Cluster cluster = JsonUtils.parse(entity.getData(), clazz);
			return cluster;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}

	}

}
