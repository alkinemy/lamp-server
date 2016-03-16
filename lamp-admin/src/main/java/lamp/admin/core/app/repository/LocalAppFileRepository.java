package lamp.admin.core.app.repository;

import lamp.admin.core.app.domain.LocalAppFile;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalAppFileRepository extends LampJpaRepository<LocalAppFile, Long> {

	List<LocalAppFile> findAllByGroupIdAndArtifactIdAndBaseVersion(String groupId, String artifactId, String baseVersion);

	Optional<LocalAppFile> findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(String repositoryId, String groupId, String artifactId, String baseVersion);

	Page<LocalAppFile> findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(String repositoryId, String groupId, String artifactId, Pageable pageable);

	List<LocalAppFile> findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(String repositoryId, String groupId, String artifactId);

	Page<LocalAppFile> findAllByRepositoryId(String repositoryId, Pageable pageable);
}
