package lamp.admin.core.agent.domain;

import lamp.admin.utils.assembler.AbstractAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class TargetServerAssembler extends AbstractAssembler<TargetServerCreateForm, TargetServer> {

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

		// TODO Password Encypt

		return targetServer;
	}

}
