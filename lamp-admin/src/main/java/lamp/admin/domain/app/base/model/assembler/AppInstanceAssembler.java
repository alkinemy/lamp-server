package lamp.admin.domain.app.base.model.assembler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.Host;
import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.host.service.HostFacadeService;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppInstanceAssembler extends AbstractListAssembler <AppInstanceEntity, AppInstance> {

	@Autowired
	private HostFacadeService hostFacadeService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override protected AppInstance doAssemble(AppInstanceEntity entity) {
		try {
			AppInstance appInstance;
			if (StringUtils.isNotBlank(entity.getData())) {
				appInstance = objectMapper.readValue(entity.getData(), AppInstance.class);
			} else {
				appInstance = new AppInstance();
			}

			appInstance.setId(entity.getId());
			appInstance.setName(entity.getName());
			appInstance.setDescription(entity.getDescription());
			appInstance.setAppId(entity.getAppId());
			appInstance.setAppVersion(entity.getAppVersion());
			appInstance.setHostId(entity.getHostId());
			appInstance.setStatus(entity.getStatus());

			appInstance.setMonitored(entity.isMonitored());

			Optional<Host> hostOptional = hostFacadeService.getHostOptional(entity.getHostId());
			if (hostOptional.isPresent()) {
				Host host = hostOptional.get();
				appInstance.setHostName(host.getName());
				appInstance.setHostAddress(host.getAddress());
			}

			return appInstance;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}

	}

}
