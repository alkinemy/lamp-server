package lamp.admin.domain.app.repo.service;

import com.mysema.query.types.Predicate;

import lamp.admin.domain.app.repo.model.LocalAppFileDto;
import lamp.admin.domain.app.repo.model.entity.LocalAppFileEntity;
import lamp.admin.domain.app.repo.model.entity.LocalAppRepoEntity;
import lamp.admin.domain.app.repo.repository.LocalAppFileEntityRepository;
import lamp.admin.domain.app.repo.service.dto.LocalAppFileUploadForm;
import lamp.admin.domain.app.repo.service.param.LocalAppFileSearchParams;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.old.app.service.AppRepoEntityService;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LocalAppFileEntityService {

	@Autowired
	private LocalAppFileEntityRepository localAppFileEntityRepository;

	@Autowired
	private AppRepoEntityService appRepoEntityService;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<LocalAppFileDto> getLocalAppFileList(LocalAppFileSearchParams searchParams, Pageable pageable) {
		Predicate predicate = searchParams.buildPredicate();
		Page<LocalAppFileEntity> page = localAppFileEntityRepository.findAll(predicate, pageable);
		return smartAssembler.assemble(pageable, page, LocalAppFileDto.class);
	}

	public Page<LocalAppFileDto> getLocalAppFileList(String repositoryId, Pageable pageable) {
		LocalAppFileSearchParams searchParams = new LocalAppFileSearchParams();
		searchParams.setRepositoryId(repositoryId);
		return getLocalAppFileList(searchParams, pageable);
	}

	public List<LocalAppFileEntity> getLocalAppFiles(String repositoryId, String groupId, String artifactId) {
		return localAppFileEntityRepository.findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(repositoryId, groupId, artifactId);
	}

	public LocalAppFileDto getLocalAppFileDto(Long id) {
		LocalAppFileEntity localAppFile = localAppFileEntityRepository.findOne(id);
		return smartAssembler.assemble(localAppFile, LocalAppFileDto.class);
	}

	@Transactional
	public LocalAppFileEntity uploadLocalAppFile(String repositoryId, LocalAppFileUploadForm editForm) throws MessageException {
		LocalAppRepoEntity appRepo = appRepoEntityService.getAppRepo(repositoryId);

		LocalAppFileEntity localAppFile = new LocalAppFileEntity();
		localAppFile.setName(editForm.getName());
		localAppFile.setDescription(editForm.getDescription());
		localAppFile.setRepositoryId(appRepo.getId());
		localAppFile.setGroupId(editForm.getGroupId());
		localAppFile.setArtifactId(editForm.getArtifactId());
		localAppFile.setBaseVersion(editForm.getVersion());
		localAppFile.setVersion(localAppFile.getBaseVersion() + "." + System.currentTimeMillis());
		localAppFile.setDeleted(false);

		Optional<LocalAppFileEntity> localAppFileFromDbOptional = localAppFileEntityRepository.findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(repositoryId, localAppFile.getGroupId(), localAppFile.getArtifactId(), localAppFile.getBaseVersion());
		if (!localAppFile.isSnapshot()) {
			Exceptions.throwsException(localAppFileFromDbOptional.isPresent(), LampErrorCode.DUPLICATED_LOCAL_APP_FILE);
		}
		
		try {
			MultipartFile uploadFile = editForm.getUploadFile();
			String originalFilename = uploadFile.getOriginalFilename();

			Path path = Paths.get(appRepo.getRepositoryPath(), String.valueOf(repositoryId), localAppFile.getGroupId(), localAppFile.getArtifactId());
			File dir = path.toFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String ext = FileUtils.getExtension(originalFilename);
			File destFile = new File(dir, localAppFile.getArtifactId() + localAppFile.getBaseVersion() + "-" + System.currentTimeMillis() + "." + ext);
			uploadFile.transferTo(destFile);
			localAppFile.setPathname(destFile.getAbsolutePath());
			localAppFile.setFilename(originalFilename);

			localAppFile.setFileSize(uploadFile.getSize());
			localAppFile.setFileDate(LocalDateTime.now());
			localAppFile.setContentType(uploadFile.getContentType());

			LocalAppFileEntity saved = localAppFileEntityRepository.save(localAppFile);
			localAppFileFromDbOptional.ifPresent(this::deleteLocalAppFile);
			return saved;
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.LOCAL_APP_FILE_UPLOAD_FAILED, e);
		}
	}

	@Transactional
	public void deleteLocalAppFile(LocalAppFileEntity localAppFile) {
		File file = new File(localAppFile.getPathname());
		if (!file.delete()) {
			log.warn("{} file delete failed", file.getAbsolutePath());
		}
		localAppFileEntityRepository.delete(localAppFile);
	}


}
