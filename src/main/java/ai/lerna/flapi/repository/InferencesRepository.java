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

	@Query(value = "SELECT CAST(count(*) FILTER (WHERE s.prediction = s.success) AS FLOAT) / COALESCE(NULLIF(CAST(count(s.*) AS FLOAT), 0.0), 1.0)\n" +
			"FROM success s, lerna_ml ml, lerna_app a WHERE s.ml_id = ml.id AND ml.app_id = a.id AND a.id = :appId AND a.user_id = :userId AND version = (SELECT MAX(version) - :versionBeforeLatest FROM success)", nativeQuery = true)
	BigDecimal getSuccesses(long userId, long appId, long versionBeforeLatest);
}
