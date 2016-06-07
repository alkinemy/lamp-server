package lamp.admin.domain.host.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;

import lamp.admin.domain.host.model.entity.HostEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostEntityRepository extends LampJpaRepository<HostEntity, String> {

	Optional<HostEntity> findOneByAddress(String address);
}
