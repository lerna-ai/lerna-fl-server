package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaML;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
