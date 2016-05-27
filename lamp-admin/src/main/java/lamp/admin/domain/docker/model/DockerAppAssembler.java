package lamp.admin.domain.docker.model;

import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class DockerAppAssembler extends AbstractListAssembler<DockerAppEntity, DockerApp> {

	@Override protected DockerApp doAssemble(DockerAppEntity dockerAppEntity) {
		return JsonUtils.parse(dockerAppEntity.getData(), DockerApp.class);
	}

}
