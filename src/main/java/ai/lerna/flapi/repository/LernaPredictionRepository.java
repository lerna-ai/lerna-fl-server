package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.LernaPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LernaPredictionRepository extends JpaRepository<LernaPrediction, Long> {
	@Override
	Optional<LernaPrediction> findById(Long userId);

	@Override
	List<LernaPrediction> findAll();
}
