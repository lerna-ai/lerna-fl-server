package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;

public interface RecommendationManager {
	void evictHostCache();

	void evictHostCache(String token);

	EventResponse sendEvent(String token, Event event);

	RecommendationItems getItems(String token, Item item);
}
