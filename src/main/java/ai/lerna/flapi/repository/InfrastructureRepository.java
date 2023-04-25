package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.Infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfrastructureRepository extends JpaRepository<Infrastructure, Long> {
	@Override
	Optional<Infrastructure> findById(Long id);

	@Override
	List<Infrastructure> findAll();
}
