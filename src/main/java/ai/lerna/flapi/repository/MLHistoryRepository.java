package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.MLHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MLHistoryRepository extends JpaRepository<MLHistory, Long> {
	@Override
	Optional<MLHistory> findById(Long id);

	@Override
	List<MLHistory> findAll();

	Optional<MLHistory> findByMLIdAndVersion(Long mlId, Long version);
}
