package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.LocalAppFile;
import lamp.admin.core.app.domain.LocalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalAppRepoService {

	@Autowired
	private LocalAppFileService localAppFileService;

	public List<String> getVersions(LocalAppRepo localAppRepo, String groupId, String artifactId) {
		List<LocalAppFile> localAppFiles = localAppFileService.getLocalAppFiles(localAppRepo.getId(), groupId, artifactId);
		return localAppFiles.stream().map(LocalAppFile::getBaseVersion).collect(Collectors.toList());
	}

}
