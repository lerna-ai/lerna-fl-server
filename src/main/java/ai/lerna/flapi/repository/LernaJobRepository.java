package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaJobRepository extends JpaRepository<LernaJob, Long> {
	@Override
	Optional<LernaJob> findById(Long jobId);

	@Override
	List<LernaJob> findAll();

	List<LernaJob> findByMLId(long mlId);

	List<LernaJob> findByMLIdAndPrediction(long mlId, String prediction);

	@Query(value = "SELECT lj.* FROM lerna_job lj INNER JOIN lerna_ml lm ON lj.ml_id = lm.id WHERE lm.app_id = :appId", nativeQuery = true)
	List<LernaJob> findAllByAppId(long appId);

	@Query(value = "SELECT lj.* FROM lerna_job lj INNER JOIN lerna_ml lm ON lj.ml_id = lm.id INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.token = :token", nativeQuery = true)
	List<LernaJob> findAllByAppToken(String token);

	@Query(value = "SELECT DISTINCT ON(lj.prediction) lj.* FROM lerna_job lj INNER JOIN lerna_ml lm ON lj.ml_id = lm.id INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.token = :token", nativeQuery = true)
	List<LernaJob> findJobPredictionMapByAppToken(String token);

	@Query(value = "SELECT MAX(lj.total_data_points) FROM lerna_job lj INNER JOIN lerna_ml lm ON lj.ml_id = lm.id INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.user_id = :userId AND la.id = :appId", nativeQuery = true)
	Long getTotalDataPoints(long userId, long appId);

	@Query(value = "SELECT lj.* FROM lerna_job lj INNER JOIN lerna_ml lm ON lj.ml_id = lm.id INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.user_id = :userId AND la.id = :appId AND lj.ml_id = :mlId", nativeQuery = true)
	List<LernaJob> getModel(long userId, long appId, long mlId);

	@Query(value = "SELECT DISTINCT ON (lj.prediction_value) lj.* FROM lerna_job lj LEFT JOIN lerna_ml lm on lm.id = lj.ml_id WHERE lm.app_id = :appId ORDER BY lj.prediction_value", nativeQuery = true)
	List<LernaJob> getCategories(long appId);

	//String updateWeights(String prediction, long mlId, INDArray weights, long total_data_points, long total_devices);
}
