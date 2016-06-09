package lamp.admin.domain.resource.repo.service;


import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.resource.repo.model.dto.AppRepoDto;
import lamp.admin.domain.resource.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.resource.repo.model.entity.LocalAppRepoEntity;
import lamp.admin.domain.resource.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.resource.repo.model.form.AppRepoCreateForm;
import lamp.admin.domain.resource.repo.model.form.AppRepoUpdateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.resource.repo.repository.AppRepoEntityRepository;
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
	private AppRepoEntityRepository appRepoEntityRepository;

	@Autowired
	private LocalAppRepoEntityService localAppRepoEntityService;

	@Autowired
	private MavenAppRepoEntityService mavenAppRepoEntityService;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<AppRepoDto> getAppRepoList(Pageable pageable) {
		Page<AppRepoEntity> page = appRepoEntityRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AppRepoEntity.class, AppRepoDto.class);
	}

	public List<AppRepoDto> getAppRepoListByType(AppResourceType repositoryType) {
		List<AppRepoEntity> list = appRepoEntityRepository.findAllByRepositoryType(repositoryType);
		return smartAssembler.assemble(list, AppRepoEntity.class, AppRepoDto.class);
	}

	public <T extends AppRepoEntity> Optional<T> getAppRepoOptional(String id) {
		return Optional.ofNullable((T) appRepoEntityRepository.findOne(id));
	}

	public <T extends AppRepoEntity> T getAppRepo(String id) {
		return (T) getAppRepoOptional(id).orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_REPOSITORY_NOT_FOUND, id));
	}

	public List<String> getVersions(String id,String groupId, String artifactId) {
		AppRepoEntity appRepo = getAppRepo(id);
		if (appRepo instanceof LocalAppRepoEntity) {
			return localAppRepoEntityService.getVersions((LocalAppRepoEntity) appRepo, groupId, artifactId);
		} else if (appRepo instanceof MavenAppRepoEntity) {
			return mavenAppRepoEntityService.getVersions((MavenAppRepoEntity) appRepo, groupId, artifactId);
		}
		return null;
	}

	public AppRepoUpdateForm getAppRepoUpdateForm(String id) {
		AppRepoEntity appRepo = getAppRepo(id);
		return smartAssembler.assemble(appRepo, AppRepoEntity.class, AppRepoUpdateForm.class);
	}


	@Transactional
	public AppRepoEntity insertAppRepo(AppRepoCreateForm editForm) {
		AppRepoEntity appRepo = smartAssembler.assemble(editForm, AppRepoCreateForm.class, AppRepoEntity.class);
		return appRepoEntityRepository.save(appRepo);
	}

	@Transactional
	public AppRepoEntity updateAppRepo(AppRepoUpdateForm editForm) {
		AppRepoEntity appRepo = getAppRepo(editForm.getId());
		smartAssembler.populate(editForm, appRepo, AppRepoEntity.class);
		return appRepo;
	}

	public boolean existAppRepoByName(String name) {
		return appRepoEntityRepository.findOneByName(name) != null;
	}
}
