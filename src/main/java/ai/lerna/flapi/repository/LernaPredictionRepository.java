package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface LernaPredictionRepository extends JpaRepository<LernaPrediction, Long> {
	@Override
	Optional<LernaPrediction> findById(Long userId);

	@Override
	List<LernaPrediction> findAll();

	@Query(value = "SELECT * FROM lerna_predictions pr WHERE timestamp = (select max(timestamp) FROM lerna_predictions WHERE pr.device_id=lerna_predictions.device_id AND pr.ml_id = :mlID)", nativeQuery = true)
	List<LernaPrediction> findLatestByMLId(Long mlID);

	@Query(value = "SELECT pr.* FROM lerna_predictions pr, lerna_ml ml, lerna_app ap WHERE timestamp = (select max(timestamp) FROM lerna_predictions WHERE pr.device_id=lerna_predictions.device_id AND pr.ml_id = ml.id AND ml.app_id = ap.id AND ap.token = :token)", nativeQuery = true)
	List<LernaPrediction> findLatestByToken(String token);
}
