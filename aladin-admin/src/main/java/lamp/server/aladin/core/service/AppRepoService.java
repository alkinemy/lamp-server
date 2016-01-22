package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.dto.AppRepoCreateForm;
import lamp.server.aladin.core.dto.AppRepoDto;
import lamp.server.aladin.core.repository.AppRepoRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppRepoService {

	@Autowired
	private AppRepoRepository appRepoRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<AppRepoDto> getAppRepositoryList(Pageable pageable) {
		Page<AppRepo> page = appRepoRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AppRepoDto.class);
	}

	public List<AppRepoDto> getAppRepositoryListByType(AppResourceType repositoryType) {
		List<AppRepo> list = appRepoRepository.findAllByRepositoryType(repositoryType);
		return smartAssembler.assemble(list, AppRepoDto.class);
	}

	@Transactional
	public AppRepo insertAppRepository(AppRepoCreateForm editForm) {
		AppRepo appRepo = smartAssembler.assemble(editForm, AppRepoCreateForm.class, AppRepo.class);
		return appRepoRepository.save(appRepo);
	}

	public <T extends AppRepo> T getAppRepository(Long id) {
		return (T) appRepoRepository.findOne(id);
	}

}
