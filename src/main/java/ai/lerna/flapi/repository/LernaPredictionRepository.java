package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
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

	@Query(value = "SELECT DISTINCT ON (device_id) pr.* FROM lerna_predictions pr, lerna_ml ml, lerna_app ap WHERE pr.ml_id = ml.id AND ml.app_id = ap.id AND ap.token = :token ORDER BY device_id, timestamp DESC", nativeQuery = true)
	List<LernaPrediction> findLatestByToken(String token);

	@Query(value = "SELECT DISTINCT ON (device_id) pr.* FROM lerna_predictions pr, lerna_ml ml, lerna_app ap WHERE pr.ml_id = ml.id AND ml.app_id = ap.id AND ap.token = :token AND timestamp > current_date - interval '1 days' ORDER BY device_id, timestamp DESC", nativeQuery = true)
	List<LernaPrediction> findLatestOneDayByToken(String token);

	@Query(value = "SELECT DATE_PART('week', p.timestamp) AS week, COUNT(DISTINCT p.device_id) AS devices FROM lerna_predictions p, lerna_ml ml, lerna_app app WHERE p.ml_id = ml.id AND ml.app_id = app.id AND ml.app_id <> 1 AND app.user_id = :userId GROUP BY DATE_PART('week', p.timestamp) ORDER BY DATE_PART('week', p.timestamp) DESC", nativeQuery = true)
	List<Map<String, BigInteger>> findDevicePredictionPerWeek(Long userId);

	@Query(value = "SELECT COUNT(DISTINCT device_id) FROM lerna_predictions p, lerna_ml ml, lerna_app a WHERE ml.id = p.ml_id AND ml.app_id = a.id AND ml.app_id <> 1 AND a.user_id = :userId", nativeQuery = true)
	long getTotalDevices(long userId);

	@Query(value = "SELECT COUNT(DISTINCT device_id) FROM (SELECT device_id FROM lerna_predictions p, lerna_ml ml, lerna_app a WHERE ml.id = p.ml_id AND ml.app_id = a.id AND a.user_id = :userId AND a.id <> 1 AND timestamp >= current_date - interval '7 days' UNION SELECT device_id FROM ml_history_datapoint hd, ml_history h, lerna_ml ml, lerna_app a WHERE hd.history_id = h.id AND ml.id = h.ml_id AND ml.app_id = a.id AND a.user_id = :userId AND a.id <> 1 AND timestamp >= current_date - interval '7 days') AS device_id", nativeQuery = true)
	long getTotalDevicesLastWeek(long userId);

	@Query(value = "SELECT COUNT(DISTINCT device_id) FROM (SELECT device_id FROM lerna_predictions p, lerna_ml ml, lerna_app a WHERE ml.id = p.ml_id AND ml.app_id = a.id AND a.user_id = :userId AND a.id <> 1 AND timestamp < current_date - interval '7 days' AND timestamp >= current_date - interval '14 days' UNION SELECT device_id FROM ml_history_datapoint hd, ml_history h, lerna_ml ml, lerna_app a WHERE hd.history_id = h.id AND ml.id = h.ml_id AND ml.app_id = a.id AND a.user_id = :userId AND a.id <> 1 AND timestamp < current_date - interval '7 days' AND timestamp >= current_date - interval '14 days') AS device_id", nativeQuery = true)
	long getTotalDevicesPreviousWeek(long userId);
}
