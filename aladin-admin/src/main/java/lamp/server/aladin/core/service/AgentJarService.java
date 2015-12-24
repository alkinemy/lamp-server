package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AgentJar;
import lamp.server.aladin.core.repository.AgentJarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgentJarService {

	@Autowired
	private AgentJarRepository agentJarRepository;

	public Optional<AgentJar> getAgentJar(Long id) {
		AgentJar agentJar = agentJarRepository.findOne(id);
		return Optional.ofNullable(agentJar);
	}
}
