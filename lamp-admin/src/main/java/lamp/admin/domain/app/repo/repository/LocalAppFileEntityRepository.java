package lamp.admin.domain.app.repo.repository;

import lamp.admin.domain.app.repo.model.entity.LocalAppFileEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalAppFileEntityRepository extends LampJpaRepository<LocalAppFileEntity, Long> {

	List<LocalAppFileEntity> findAllByGroupIdAndArtifactIdAndBaseVersion(String groupId, String artifactId, String baseVersion);

	Optional<LocalAppFileEntity> findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(String repositoryId, String groupId, String artifactId,
																					  String baseVersion);

	Page<LocalAppFileEntity> findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(String repositoryId, String groupId, String artifactId,
																					  Pageable pageable);

	List<LocalAppFileEntity> findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(String repositoryId, String groupId, String artifactId);

	Page<LocalAppFileEntity> findAllByRepositoryId(String repositoryId, Pageable pageable);
}
