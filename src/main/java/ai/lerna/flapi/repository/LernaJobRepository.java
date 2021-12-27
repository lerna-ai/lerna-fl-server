package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.nd4j.linalg.api.ndarray.INDArray;

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
	
	//String updateWeights(String prediction, long mlId, INDArray weights, long total_data_points, long total_devices);
}
