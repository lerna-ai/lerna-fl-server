package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaAppRepository extends JpaRepository<LernaApp, Long> {
	@Override
	Optional<LernaApp> findById(Long appId);

	@Override
	List<LernaApp> findAll();
}
