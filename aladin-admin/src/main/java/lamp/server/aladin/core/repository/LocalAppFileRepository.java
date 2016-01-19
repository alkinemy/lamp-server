package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.LocalAppFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalAppFileRepository extends JpaRepository<LocalAppFile, Long> {

	Optional<LocalAppFile> findOneByGroupIdAndArtifactIdAndVersion(String groupId, String artifactId, String version);

	Page<LocalAppFile> findAllByGroupIdAndArtifactIdOrderByVersionDesc(String groupId, String artifactId, Pageable pageable);
}
