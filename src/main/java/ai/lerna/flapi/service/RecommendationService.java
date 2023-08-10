package ai.lerna.flapi.service;

import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.service.actionML.dto.ItemResponse;

public interface RecommendationService {

	EventResponse sendEvent(String host, Event event);

	ItemResponse getItems(String host, Item item);
}
