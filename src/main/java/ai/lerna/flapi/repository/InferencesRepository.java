package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.Inferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InferencesRepository extends JpaRepository<Inferences, Long> {
	@Override
	Optional<Inferences> findById(Long id);

	@Override
	List<Inferences> findAll();

	Optional<Inferences> findByVersion(Long version);
}
