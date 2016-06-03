package lamp.admin.domain.docker.model;

import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class DockerAppEntityAssembler extends AbstractListAssembler<DockerApp, DockerAppEntity> {

	@Override protected DockerAppEntity doAssemble(DockerApp dockerApp) {
		DockerAppEntity dockerAppEntity = new DockerAppEntity();
		dockerAppEntity.setId(dockerApp.getId());
		dockerAppEntity.setGroupId(dockerApp.getGroupId());
		dockerAppEntity.setData(JsonUtils.stringify(dockerApp));
		return dockerAppEntity;
	}

}
