package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.MLHistoryDatapoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MLHistoryDatapointRepository extends JpaRepository<MLHistoryDatapoint, Long> {
	@Override
	Optional<MLHistoryDatapoint> findById(Long userId);

	@Override
	List<MLHistoryDatapoint> findAll();

	@Query(value = "SELECT COUNT(DISTINCT device_id) FROM ml_history_datapoint hd, ml_history h, lerna_ml ml, lerna_app a WHERE hd.history_id = h.id AND ml.id = h.ml_id AND ml.app_id = a.id AND a.user_id = :userId", nativeQuery = true)
	long getTotalDevices(long userId);

	@Query(value = "SELECT COUNT(DISTINCT device_id) FROM ml_history_datapoint hd, ml_history h, lerna_ml ml, lerna_app a WHERE hd.history_id = h.id AND ml.id = h.ml_id AND ml.app_id = a.id AND a.user_id = :userId AND timestamp >= current_date - interval '7 days'", nativeQuery = true)
	long getTotalDevicesLastWeek(long userId);
}
