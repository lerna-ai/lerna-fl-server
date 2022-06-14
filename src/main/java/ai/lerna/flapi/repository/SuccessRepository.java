package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.Success;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuccessRepository extends JpaRepository<Success, Long> {
	@Override
	Optional<Success> findById(Long userId);

	@Override
	List<Success> findAll();

}
