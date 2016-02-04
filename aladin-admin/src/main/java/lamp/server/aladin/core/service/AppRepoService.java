package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.dto.AppRepoCreateForm;
import lamp.server.aladin.core.dto.AppRepoDto;
import lamp.server.aladin.core.dto.AppRepoUpdateForm;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.repository.AppRepoRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppRepoService {

	@Autowired
	private AppRepoRepository appRepoRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<AppRepoDto> getAppRepoList(Pageable pageable) {
		Page<AppRepo> page = appRepoRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AppRepo.class, AppRepoDto.class);
	}

	public List<AppRepoDto> getAppRepoListByType(AppResourceType repositoryType) {
		List<AppRepo> list = appRepoRepository.findAllByRepositoryType(repositoryType);
		return smartAssembler.assemble(list, AppRepo.class, AppRepoDto.class);
	}

	public <T extends AppRepo> Optional<T> getAppRepoOptional(Long id) {
		return Optional.ofNullable((T) appRepoRepository.findOne(id));
	}

	public <T extends AppRepo> T getAppRepo(Long id) {
		return (T) getAppRepoOptional(id).orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_REPOSITORY_NOT_FOUND, id));
	}

	public AppRepoUpdateForm getAppRepoUpdateForm(Long id) {
		AppRepo appRepo = getAppRepo(id);
		return smartAssembler.assemble(appRepo, AppRepo.class, AppRepoUpdateForm.class);
	}


	@Transactional
	public AppRepo insertAppRepo(AppRepoCreateForm editForm) {
		AppRepo appRepo = smartAssembler.assemble(editForm, AppRepoCreateForm.class, AppRepo.class);
		return appRepoRepository.save(appRepo);
	}

	@Transactional
	public AppRepo updateAppRepo(AppRepoUpdateForm editForm) {
		AppRepo appRepo = getAppRepo(editForm.getId());
		smartAssembler.populate(editForm, appRepo, AppRepo.class);
		return appRepo;
	}

	public boolean existAppRepoByName(String name) {
		return appRepoRepository.findOneByName(name) != null;
	}
}
