package ai.lerna.flapi.repository;

import ai.lerna.flapi.entity.WebhookConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookConfigRepository extends JpaRepository<WebhookConfig, Long> {
	@Override
	List<WebhookConfig> findAll();

	List<WebhookConfig> findAllByEnabledTrue();
}
