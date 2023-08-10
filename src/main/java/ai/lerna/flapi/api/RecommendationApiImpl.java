package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.manager.RecommendationManager;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationApiImpl implements RecommendationApi {

	private final RecommendationManager recommendationManager;
	private final TrainingApiValidator validator;

	@Autowired
	public RecommendationApiImpl(RecommendationManager recommendationManager, TrainingApiValidator validator) {
		this.recommendationManager = recommendationManager;
		this.validator = validator;
	}

	@Override
	public ResponseEntity<EventResponse> submitEvent(String token, Event event) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.sendEvent(token, event));
	}

	@Override
	public ResponseEntity<RecommendationItems> getItemsByUser(String token, String user) throws Exception {
		validator.tokenValidation(token);
		Item item = Item.newBuilder().setUser(user).build();
		return ResponseEntity.ok(recommendationManager.getItems(token, item));
	}

	@Override
	public ResponseEntity<RecommendationItems> getItemsByNum(String token, Integer num) throws Exception {
		validator.tokenValidation(token);
		Item item = Item.newBuilder().setNum(num).build();
		return ResponseEntity.ok(recommendationManager.getItems(token, item));
	}

	@Override
	public ResponseEntity<RecommendationItems> getItems(String token, Item item) throws Exception {
		validator.tokenValidation(token);
		return ResponseEntity.ok(recommendationManager.getItems(token, item));
	}
}
