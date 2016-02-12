package lamp.server.aladin.core.service;

import com.mysema.query.types.Predicate;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.core.domain.LocalAppFile;
import lamp.server.aladin.core.domain.LocalAppRepo;
import lamp.server.aladin.core.dto.LocalAppFileDto;
import lamp.server.aladin.core.dto.LocalAppFileSearchParams;
import lamp.server.aladin.core.dto.LocalAppFileUploadForm;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.exception.MessageException;
import lamp.server.aladin.core.repository.LocalAppFileRepository;
import lamp.server.aladin.utils.StringUtils;
import lamp.server.aladin.utils.assembler.SmartAssembler;
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
import java.util.Optional;

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

		if (!localAppFile.isSnapshot()) {
			Optional<LocalAppFile> localAppFileFromDbOptional = localAppFileRepository.findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(repositoryId, localAppFile.getGroupId(), localAppFile.getArtifactId(), localAppFile.getBaseVersion());
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
			localAppFile.setContentType(uploadFile.getContentType());
			return localAppFileRepository.save(localAppFile);
		} catch (Exception e) {
			throw Exceptions.newException(AdminErrorCode.LOCAL_APP_FILE_UPLOAD_FAILED, e);
		}
	}


}
