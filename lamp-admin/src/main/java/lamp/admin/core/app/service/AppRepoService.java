package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.*;
import lamp.admin.core.app.repository.AppRepoRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
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
	private LocalAppRepoService localAppRepoService;

	@Autowired
	private MavenAppRepoService mavenAppRepoService;

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

	public List<String> getVersions(Long id,String groupId, String artifactId) {
		AppRepo appRepo = getAppRepo(id);
		if (appRepo instanceof LocalAppRepo) {
			return localAppRepoService.getVersions((LocalAppRepo) appRepo, groupId, artifactId);
		} else if (appRepo instanceof MavenAppRepo) {
			return mavenAppRepoService.getVersions((MavenAppRepo) appRepo, groupId, artifactId);
		}
		return null;
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
