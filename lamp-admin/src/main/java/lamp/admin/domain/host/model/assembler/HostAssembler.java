package lamp.admin.domain.host.model.assembler;

import lamp.admin.core.host.Host;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class HostAssembler extends AbstractListAssembler<HostEntity, Host> {

	@Override protected Host doAssemble(HostEntity entity) {
		if (StringUtils.isNotBlank(entity.getData())) {
			try {
				String dataType = StringUtils.defaultIfBlank(entity.getDataType(), Host.class.getName());
				Class<? extends Host> clazz = (Class<? extends Host>) Class.forName(dataType);
				Host host = JsonUtils.parse(entity.getData(), clazz);
				BeanUtils.copyProperties(entity, host);
				return host;
			} catch (Exception e) {
				throw new CannotAssembleException(e);
			}
		} else {
			Host host = new Host();
			BeanUtils.copyProperties(entity, host);
			return host;
		}
	}

}
