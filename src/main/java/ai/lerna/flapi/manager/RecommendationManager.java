package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.service.actionML.dto.EngineConfig;
import ai.lerna.flapi.api.dto.RecommendationEngineRequest;
import ai.lerna.flapi.api.dto.RecommendationEvent;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;

import java.util.List;

public interface RecommendationManager {
	void evictHostCache();

	void evictHostCache(String token);

	EventResponse createEngine(String token, String engineId);

	EventResponse createEngine(String token, RecommendationEngineRequest engineRequest);

	EventResponse updateEngine(String token, String engineId);

	EventResponse updateEngine(String token, RecommendationEngineRequest engineRequest);

	void deleteEngine(String token, String engineId);

	EventResponse trainEngine(String token, String engineId);

	EventResponse cancelTrainEngine(String token, String engineId, String jobId);

	List<EngineConfig> getEnginesStatus(String token);

	EngineConfig getEngineStatus(String token, String engineId);

	EventResponse sendEvent(String token, RecommendationEvent event);

	RecommendationItems getItems(String token, Item item);
}
