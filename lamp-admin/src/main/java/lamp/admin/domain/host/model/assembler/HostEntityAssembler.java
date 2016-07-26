package lamp.admin.domain.host.model.assembler;


import lamp.admin.core.host.Host;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class HostEntityAssembler extends AbstractListAssembler<Host, HostEntity> implements Populater<Host, HostEntity> {

	@Override protected HostEntity doAssemble(Host host) {
		HostEntity entity = new HostEntity();
		BeanUtils.copyProperties(host, entity);

		entity.setDataType(host.getClass().getName());
		entity.setData(JsonUtils.stringify(host));
		return entity;
	}

	@Override public void populate(Host host, HostEntity entity) {
		entity.setName(host.getName());
		entity.setDescription(host.getDescription());

		entity.setDataType(host.getClass().getName());
		entity.setData(JsonUtils.stringify(host));
	}
}
