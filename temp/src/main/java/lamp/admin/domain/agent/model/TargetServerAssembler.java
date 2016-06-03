package lamp.admin.domain.agent.model;

import lamp.admin.domain.agent.service.SshKeyService;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractAssembler;
import lamp.common.utils.assembler.Populater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Slf4j
@Component
public class TargetServerAssembler extends AbstractAssembler<TargetServerCreateForm, TargetServer> implements Populater<TargetServerUpdateForm, TargetServer> {

	@Autowired
	private SshKeyService sshKeyService;

	@Override protected TargetServer doAssemble(TargetServerCreateForm targetServerCreateForm) {
		TargetServer targetServer = new TargetServer();
		BeanUtils.copyProperties(targetServerCreateForm, targetServer);

		if (StringUtils.isBlank(targetServer.getId())) {
			targetServer.setId(UUID.randomUUID().toString());
		}

		if (StringUtils.isBlank(targetServer.getAddress())) {
			try {
				InetAddress inetAddress;

				if ("localhost".equals(targetServer.getHostname())) {
					inetAddress = InetAddress.getLocalHost();
				} else {
					inetAddress = InetAddress.getByName(targetServer.getHostname());
				}

				targetServer.setHostname(inetAddress.getHostName());
				targetServer.setAddress(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				log.warn("TargetServer UnknownHost", e);
			}

		}


		if (SshAuthType.KEY.equals(targetServerCreateForm.getSshAuthType())) {
			SshKey sshKey = sshKeyService.getSshKey(targetServerCreateForm.getSshKeyId());
			targetServer.setSshKeyId(sshKey.getId());
			targetServer.setSshUsername(sshKey.getUsername());
			targetServer.setSshPassword(null);
		} else if (SshAuthType.PASSWORD.equals(targetServerCreateForm.getSshAuthType())) {
			targetServer.setSshKeyId(null);
			targetServer.setSshUsername(targetServerCreateForm.getSshUsername());
			targetServer.setSshPassword(targetServerCreateForm.getSshPassword());
		}

		// TODO Password Encrypt

		return targetServer;
	}

	@Override public void populate(TargetServerUpdateForm form, TargetServer entity) {
		BeanUtils.copyProperties(form, entity);

		if (SshAuthType.KEY.equals(form.getSshAuthType())) {
			SshKey sshKey = sshKeyService.getSshKey(form.getSshKeyId());
			entity.setSshKeyId(sshKey.getId());
			entity.setSshUsername(sshKey.getUsername());
			entity.setSshPassword(null);
		} else if (SshAuthType.PASSWORD.equals(form.getSshAuthType())) {
			entity.setSshKeyId(null);
			entity.setSshUsername(form.getSshUsername());
			entity.setSshPassword(form.getSshPassword());
		}
	}
}
