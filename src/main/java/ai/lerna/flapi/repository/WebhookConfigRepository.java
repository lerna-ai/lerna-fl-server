package ai.lerna.flapi.repository;

import ai.lerna.flapi.api.dto.Webhook;
import ai.lerna.flapi.entity.WebhookConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookConfigRepository extends JpaRepository<WebhookConfig, Long> {
	@Override
	List<WebhookConfig> findAll();

	List<WebhookConfig> findAllByEnabledTrue();

	void save(Webhook webhook);

	@Query(value = "SELECT wc.* FROM webhook_config wc LEFT JOIN lerna_app la on wc.app_id = la.id WHERE wc.app_id = :appId AND la.user_id = :userId", nativeQuery = true)
	List<WebhookConfig> getWebhookConfig(long userId, long appId);
}
