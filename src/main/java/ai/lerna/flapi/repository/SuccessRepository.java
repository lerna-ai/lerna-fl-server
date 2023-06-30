package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.Success;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SuccessRepository extends JpaRepository<Success, Long> {
	@Override
	Optional<Success> findById(Long userId);

	@Override
	List<Success> findAll();

	@Query(value = "SELECT CAST(count(*) FILTER (WHERE s.prediction = s.success) AS FLOAT) / COALESCE(NULLIF(CAST(count(s.*) AS FLOAT), 0.0), 1.0)\n" +
			"FROM success s, lerna_ml ml, lerna_app a " +
			"WHERE s.ml_id = ml.id " +
			"AND ml.app_id = a.id " +
			"AND a.id = :appId " +
			"AND a.user_id = :userId " +
			"AND version = (SELECT MAX(s1.version) - :versionBeforeLatest FROM success s1, lerna_ml ml1 WHERE s1.ml_id = ml1.id AND ml1.app_id = :appId) " +
			"AND (s.metadata->>'abrandom' = 'false' OR s.metadata->>'abrandom' IS NULL)", nativeQuery = true)
	BigDecimal getSuccesses(long userId, long appId, long versionBeforeLatest);

	@Query(value = "SELECT CAST(count(*) FILTER (WHERE s.prediction = s.success) AS FLOAT) / COALESCE(NULLIF(CAST(count(s.*) AS FLOAT), 0.0), 1.0)\n" +
			"FROM success s, lerna_ml ml, lerna_app a " +
			"WHERE s.ml_id = ml.id " +
			"AND ml.app_id = a.id " +
			"AND a.id = :appId " +
			"AND a.user_id = :userId " +
			"AND version = (SELECT MAX(s1.version) - :versionBeforeLatest FROM success s1, lerna_ml ml1 WHERE s1.ml_id = ml1.id AND ml1.app_id = :appId) " +
			"AND s.metadata->>'abrandom' = 'true'", nativeQuery = true)
	BigDecimal getSuccessesRandomAB(long userId, long appId, long versionBeforeLatest);
}
