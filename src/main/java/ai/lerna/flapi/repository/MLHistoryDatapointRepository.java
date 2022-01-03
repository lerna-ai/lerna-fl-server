package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.MLHistoryDatapoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MLHistoryDatapointRepository extends JpaRepository<MLHistoryDatapoint, Long> {
	@Override
	Optional<MLHistoryDatapoint> findById(Long userId);

	@Override
	List<MLHistoryDatapoint> findAll();
}
