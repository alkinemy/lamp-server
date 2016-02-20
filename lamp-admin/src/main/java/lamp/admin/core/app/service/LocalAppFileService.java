package lamp.admin.core.app.service;

import com.mysema.query.types.Predicate;
import lamp.admin.core.app.domain.*;
import lamp.admin.core.app.repository.LocalAppFileRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.core.base.exception.MessageException;
import lamp.admin.utils.StringUtils;
import lamp.admin.utils.assembler.SmartAssembler;
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
public class LocalAppFileService {

	@Autowired
	private LocalAppFileRepository localAppFileRepository;

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private SmartAssembler smartAssembler;

	public Page<LocalAppFileDto> getLocalAppFileList(LocalAppFileSearchParams searchParams, Pageable pageable) {
		Predicate predicate = searchParams.buildPredicate();
		Page<LocalAppFile> page = localAppFileRepository.findAll(predicate, pageable);
		return smartAssembler.assemble(pageable, page, LocalAppFileDto.class);
	}

	public Page<LocalAppFileDto> getLocalAppFileList(Long repositoryId, Pageable pageable) {
		LocalAppFileSearchParams searchParams = new LocalAppFileSearchParams();
		searchParams.setRepositoryId(repositoryId);
		return getLocalAppFileList(searchParams, pageable);
	}

	public List<LocalAppFile> getLocalAppFiles(Long repositoryId, String groupId, String artifactId) {
		return localAppFileRepository.findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(repositoryId, groupId, artifactId);
	}

	public LocalAppFileDto getLocalAppFileDto(Long id) {
		LocalAppFile localAppFile = localAppFileRepository.findOne(id);
		return smartAssembler.assemble(localAppFile, LocalAppFileDto.class);
	}

	@Transactional
	public LocalAppFile uploadLocalAppFile(Long repositoryId, LocalAppFileUploadForm editForm) throws MessageException {
		LocalAppRepo appRepo = appRepoService.getAppRepo(repositoryId);

		LocalAppFile localAppFile = new LocalAppFile();
		localAppFile.setName(editForm.getName());
		localAppFile.setDescription(editForm.getDescription());
		localAppFile.setRepositoryId(appRepo.getId());
		localAppFile.setGroupId(editForm.getGroupId());
		localAppFile.setArtifactId(editForm.getArtifactId());
		localAppFile.setBaseVersion(editForm.getVersion());
		localAppFile.setVersion(localAppFile.getBaseVersion() + "." + System.currentTimeMillis());
		localAppFile.setDeleted(false);

		Optional<LocalAppFile> localAppFileFromDbOptional = localAppFileRepository.findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(repositoryId, localAppFile.getGroupId(), localAppFile.getArtifactId(), localAppFile.getBaseVersion());
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

			LocalAppFile saved = localAppFileRepository.save(localAppFile);
			localAppFileFromDbOptional.ifPresent(this::deleteLocalAppFile);
			return saved;
		} catch (Exception e) {
			throw Exceptions.newException(LampErrorCode.LOCAL_APP_FILE_UPLOAD_FAILED, e);
		}
	}

	@Transactional
	public void deleteLocalAppFile(LocalAppFile localAppFile) {
		File file = new File(localAppFile.getPathname());
		if (!file.delete()) {
			log.warn("{} file delete failed", file.getAbsolutePath());
		}
		localAppFileRepository.delete(localAppFile);
	}


}
