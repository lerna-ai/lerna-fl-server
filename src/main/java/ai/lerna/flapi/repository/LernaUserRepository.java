package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaUserRepository extends JpaRepository<LernaUser, Long> {
	@Override
	Optional<LernaUser> findById(Long userId);

	@Override
	List<LernaUser> findAll();
}
