package lamp.admin.core.agent.domain;

import lamp.admin.core.agent.service.SshKeyService;
import lamp.common.utils.assembler.AbstractAssembler;
import lamp.common.utils.assembler.Populater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class TargetServerAssembler extends AbstractAssembler<TargetServerCreateForm, TargetServer> implements Populater<TargetServerUpdateForm, TargetServer> {

	@Autowired
	private SshKeyService sshKeyService;

	@Override protected TargetServer doAssemble(TargetServerCreateForm targetServerCreateForm) {
		TargetServer targetServer = new TargetServer();
		BeanUtils.copyProperties(targetServerCreateForm, targetServer);
		if ("localhost".equals(targetServer.getHostname())) {
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				targetServer.setHostname(inetAddress.getHostName());
			} catch (UnknownHostException e) {
				log.warn("TargetServer UnknownHost", e);
			}
		}

		if (SshAuthType.KEY.equals(targetServerCreateForm.getAuthType())) {
			SshKey sshKey = sshKeyService.getSshKey(targetServerCreateForm.getSshKeyId());
			targetServer.setSshKeyId(sshKey.getId());
			targetServer.setUsername(sshKey.getUsername());
			targetServer.setPassword(null);
		} else if (SshAuthType.PASSWORD.equals(targetServerCreateForm.getAuthType())) {
			targetServer.setSshKeyId(null);
			targetServer.setUsername(targetServerCreateForm.getUsername());
			targetServer.setPassword(targetServerCreateForm.getPassword());
		}

		// TODO Password Encrypt

		return targetServer;
	}

	@Override public void populate(TargetServerUpdateForm form, TargetServer entity) {
		BeanUtils.copyProperties(form, entity);

		if (SshAuthType.KEY.equals(form.getAuthType())) {
			SshKey sshKey = sshKeyService.getSshKey(form.getSshKeyId());
			entity.setSshKeyId(sshKey.getId());
			entity.setUsername(sshKey.getUsername());
			entity.setPassword(null);
		} else if (SshAuthType.PASSWORD.equals(form.getAuthType())) {
			entity.setSshKeyId(null);
			entity.setUsername(form.getUsername());
			entity.setPassword(form.getPassword());
		}
	}
}
