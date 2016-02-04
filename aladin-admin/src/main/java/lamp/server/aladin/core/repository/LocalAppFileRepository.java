package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.LocalAppFile;
import lamp.server.aladin.core.dto.LocalAppFileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalAppFileRepository extends LampRepository<LocalAppFile, Long> {

	List<LocalAppFile> findAllByGroupIdAndArtifactIdAndBaseVersion(String groupId, String artifactId, String baseVersion);
	Optional<LocalAppFile> findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(Long repositoryId, String groupId, String artifactId, String baseVersion);

	Page<LocalAppFile> findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(Long repositoryId, String groupId, String artifactId, Pageable pageable);

	Page<LocalAppFile> findAllByRepositoryId(Long repositoryId, Pageable pageable);
}
