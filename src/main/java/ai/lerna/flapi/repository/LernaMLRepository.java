package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaMLRepository extends JpaRepository<LernaML, Long> {
	@Override
	Optional<LernaML> findById(Long mlId);

	@Override
	List<LernaML> findAll();

	List<LernaML> findByAppId(long id);

	@Query(value = "SELECT lm.* FROM lerna_ml lm INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.token = :token", nativeQuery = true)
	List<LernaML> findAllByAppToken(String token);

	@Query(value = "SELECT count(lm.id) != 0 FROM lerna_ml lm INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.token = :token", nativeQuery = true)
	boolean existsByAppToken(String token);

	@Query(value = "SELECT count(lm.id) != 0 FROM lerna_ml lm INNER JOIN lerna_app la ON la.id = lm.app_id WHERE la.token = :token and lm.id = :mlId", nativeQuery = true)
	boolean existsByAppTokenAndMlId(String token, long mlId);

	@Query(value = "SELECT no_min_users FROM lerna_app WHERE token = :token", nativeQuery = true)
	int findUsersNumByAppToken(String token);

	@Modifying
	@Query(value = "UPDATE lerna_ml set accuracy = COALESCE((SELECT accuracy_avg FROM ml_history WHERE ml_id = :id AND version = (SELECT MAX(version)-1 FROM ml_history WHERE ml_id = :id)), 0.0) where id = :id", nativeQuery = true)
	void updateAccuracy(long id);
}
