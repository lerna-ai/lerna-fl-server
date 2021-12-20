package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaJobRepository extends JpaRepository<LernaJob, Long> {
	@Override
	Optional<LernaJob> findById(Long jobId);

	@Override
	List<LernaJob> findAll();
	
	List<LernaJob> findByMLId(long id);
	
}
