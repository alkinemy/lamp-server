package lamp.admin.domain.docker.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.docker.model.DockerAppEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DockerAppEntityRepository extends LampJpaRepository<DockerAppEntity, String> {


}
