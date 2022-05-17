package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.Inferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface InferencesRepository extends JpaRepository<Inferences, Long> {
	@Override
	Optional<Inferences> findById(Long id);

	@Override
	List<Inferences> findAll();

	Optional<Inferences> findByVersion(Long version);

	@Query(value = "SELECT COALESCE((SELECT AVG(accuracy) FROM inferences WHERE version = (SELECT MAX(version)-1 FROM inferences)),0)", nativeQuery = true)
	BigDecimal getLatestSuccesses();

	@Query(value = "SELECT COALESCE((SELECT AVG(accuracy) FROM inferences WHERE version = (SELECT MAX(version)-2 FROM inferences)),0)", nativeQuery = true)
	BigDecimal getPreviousSuccesses();
}
