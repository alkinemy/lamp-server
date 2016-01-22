package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.domain.LocalAppFile;
import lamp.server.aladin.core.domain.LocalAppRepo;
import lamp.server.aladin.core.dto.LocalAppFileDto;
import lamp.server.aladin.core.dto.LocalAppFileUploadForm;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.repository.AppRepoRepository;
import lamp.server.aladin.core.repository.LocalAppFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class LocalAppFileService {

	@Autowired
	private LocalAppFileRepository localAppFileRepository;

	@Autowired
	private AppRepoService appRepoService;

	public Page<LocalAppFileDto> getLocalAppFileList(Long repositoryId, Pageable pageable) {
		return localAppFileRepository.findAllByRepositoryId(repositoryId, pageable);
	}

	@Transactional
	public LocalAppFile uploadLocalAppFile(Long repositoryId, LocalAppFileUploadForm editForm) {
		LocalAppRepo appRepo = appRepoService.getAppRepository(repositoryId);

		LocalAppFile localAppFile = new LocalAppFile();
		localAppFile.setName(editForm.getName());
		localAppFile.setDescription(editForm.getDescription());
		localAppFile.setRepositoryId(appRepo.getId());
		localAppFile.setGroupId(editForm.getGroupId());
		localAppFile.setArtifactId(editForm.getArtifactId());
		localAppFile.setVersion(editForm.getVersion());
		localAppFile.setDeleted(false);

		MultipartFile uploadFile = editForm.getUploadFile();
		// TODO upload file
		localAppFile.setFilename(uploadFile.getOriginalFilename());
		return localAppFileRepository.save(localAppFile);
	}

}
