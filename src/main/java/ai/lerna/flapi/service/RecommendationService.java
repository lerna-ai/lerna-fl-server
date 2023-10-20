package ai.lerna.flapi.service;

import ai.lerna.flapi.service.actionML.dto.Engine;
import ai.lerna.flapi.service.actionML.dto.EngineConfig;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.service.actionML.dto.ItemResponse;

public interface RecommendationService {

	EventResponse createEngine(String host, Engine engine);

	EventResponse updateEngine(String host, Engine engine);

	void deleteEngine(String host, String engineId);

	EventResponse trainEngine(String host, String engineId);

	EventResponse cancelTrainEngine(String host, String engineId, String jobId);

	EngineConfig[] statusEngines(String host);

	EngineConfig statusEngine(String host, String engineId);

	EventResponse sendEvent(String host, Event event);

	ItemResponse getItems(String host, Item item);
}
