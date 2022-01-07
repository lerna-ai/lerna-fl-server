package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.MLHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
