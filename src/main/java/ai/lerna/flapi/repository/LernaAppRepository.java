package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaAppRepository extends JpaRepository<LernaApp, Long> {
	@Override
	Optional<LernaApp> findById(Long appId);

	@Override
	List<LernaApp> findAll();

	List<LernaApp> findByUserIdOrderById(Long userId);

	Optional<LernaApp> findByToken(String token);

	@Query(value = "SELECT * FROM lerna_app WHERE user_id = :userId AND id = :appId", nativeQuery = true)
	Optional<LernaApp> getByUserId(long userId, long appId);

	@Modifying
	@Query(value = "UPDATE lerna_app SET current_version = current_version + 1 WHERE token = :token", nativeQuery = true)
	void incrementVersionByToken(String token);

	@Query(value = "SELECT current_version FROM lerna_app WHERE token = :token LIMIT 1", nativeQuery = true)
	Optional<Long> getVersionByToken(String token);
	
	@Query(value = "SELECT token FROM lerna_app", nativeQuery = true)
	List<String> getTokens();
}
