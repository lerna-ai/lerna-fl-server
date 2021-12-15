package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaML;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaMLRepository extends JpaRepository<LernaML, Long> {
	@Override
	Optional<LernaML> findById(Long mlId);

	@Override
	List<LernaML> findAll();
}
