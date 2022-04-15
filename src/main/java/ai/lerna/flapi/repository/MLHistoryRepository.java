package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.MLHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MLHistoryRepository extends JpaRepository<MLHistory, Long> {
	@Override
	Optional<MLHistory> findById(Long id);

	@Override
	List<MLHistory> findAll();

	Optional<MLHistory> findByMLIdAndVersion(Long mlId, Long version);

	@Modifying
	@Query(value = "UPDATE ml_history set accuracy_avg = (SELECT AVG(accuracy) FROM ml_history_datapoint WHERE history_id = :id) WHERE id = :id", nativeQuery = true)
	void updateAccuracyAverage(long id);

	@Query(value = "SELECT accuracy_avg from ml_history where ml_id = (SELECT h.ml_id FROM ml_history h, lerna_ml ml, lerna_app a WHERE h.ml_id <> 1 AND ml.id = h.ml_id AND ml.app_id = a.id AND h.accuracy_avg > 0 AND a.user_id = :userId ORDER BY h.version DESC, h.accuracy_avg DESC LIMIT 1) ORDER BY version", nativeQuery = true)
	List<BigDecimal> getAccuracies(long userId);
}
