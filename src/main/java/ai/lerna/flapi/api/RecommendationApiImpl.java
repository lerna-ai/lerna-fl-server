package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.RecommendationCategoryItem;
import ai.lerna.flapi.api.dto.RecommendationEngineRequest;
import ai.lerna.flapi.api.dto.RecommendationEvent;
import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.manager.RecommendationManager;
import ai.lerna.flapi.api.dto.RecommendationCategoryItem;
import ai.lerna.flapi.service.actionML.dto.EngineConfig;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.validation.RecommendationApiValidator;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendationApiImpl implements RecommendationApi {

	private final RecommendationManager recommendationManager;
	private final TrainingApiValidator validator;
	private final RecommendationApiValidator recommendationApiValidator;

	@Autowired
	public RecommendationApiImpl(RecommendationManager recommendationManager, TrainingApiValidator validator, RecommendationApiValidator recommendationApiValidator) {
		this.recommendationManager = recommendationManager;
		this.validator = validator;
		this.recommendationApiValidator = recommendationApiValidator;
	}

	@Override
	public ResponseEntity<EventResponse> createEngine(String token, String engineId) throws Exception {
		validator.tokenValidation(token);
		recommendationApiValidator.validate(engineId);
		return ResponseEntity.ok(recommendationManager.createEngine(token, engineId));
	}

	@Override
	public ResponseEntity<EventResponse> createEngine(String token, RecommendationEngineRequest engineRequest) throws Exception {
		validator.tokenValidation(token);
		recommendationApiValidator.validate(engineRequest);
		return ResponseEntity.ok(recommendationManager.createEngine(token, engineRequest));
	}

	@Override
	public ResponseEntity<EventResponse> updateEngine(String token, String engineId) throws Exception {
		validator.tokenValidation(token);
		recommendationApiValidator.validate(engineId);
		return ResponseEntity.ok(recommendationManager.updateEngine(token, engineId));
	}

	@Override
	public ResponseEntity<EventResponse> updateEngine(String token, RecommendationEngineRequest engineRequest) throws Exception {
		validator.tokenValidation(token);
		recommendationApiValidator.validate(engineRequest);
		return ResponseEntity.ok(recommendationManager.updateEngine(token, engineRequest));
	}

	@Override
	public ResponseEntity<EventResponse> deleteEngine(String token, String engineId) throws Exception {
		validator.tokenValidation(token);
		recommendationManager.deleteEngine(token, engineId);
		EventResponse eventResponse = new EventResponse();
		eventResponse.setComment("Engine " + engineId + " deleted successfully");
		return ResponseEntity.ok(eventResponse);
	}

	@Override
	public ResponseEntity<EventResponse> trainEngine(String token, String engineId) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.trainEngine(token, engineId));
	}

	@Override
	public ResponseEntity<EventResponse> cancelTrainEngine(String token, String engineId, String jobId) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.cancelTrainEngine(token, engineId, jobId));
	}

	@Override
	public ResponseEntity<List<EngineConfig>> getEnginesStatus(String token) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.getEnginesStatus(token));
	}

	@Override
	public ResponseEntity<EngineConfig> getEngineStatus(String token, String engineId) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.getEngineStatus(token, engineId));
	}

	@Override
	public ResponseEntity<EventResponse> submitEvent(String token, RecommendationEvent event) throws Exception {
		validator.tokenValidation(token);
		recommendationApiValidator.validate(event.getEngineId());
		return ResponseEntity.ok(recommendationManager.sendEvent(token, event));
	}

	@Override
	public ResponseEntity<EventResponse> submitItem(String token, RecommendationCategoryItem item) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.sendItem(token, item));
	}

	@Override
	public ResponseEntity<RecommendationItems> getItemsByUser(String token, String user, String engineId) throws Exception {
		validator.tokenValidation(token);
		Item item = Item.newBuilder().setUser(user).setEngineId(engineId).build();
		return ResponseEntity.ok(recommendationManager.getItems(token, item));
	}

	@Override
	public ResponseEntity<RecommendationItems> getItemsByNum(String token, Integer num, String engineId) throws Exception {
		validator.tokenValidation(token);
		Item item = Item.newBuilder().setNum(num).setEngineId(engineId).build();
		return ResponseEntity.ok(recommendationManager.getItems(token, item));
	}

	@Override
	public ResponseEntity<RecommendationItems> getItems(String token, Item item) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.getItems(token, item));
	}
}
