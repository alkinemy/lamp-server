package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppFile;
import lamp.server.aladin.core.repository.AppFileRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AppFileService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private AppFileRepository appFileRepository;


	public Optional<AppFile> getAppFile(Long id) {
		return Optional.ofNullable(appFileRepository.findOne(id));
	}


}
