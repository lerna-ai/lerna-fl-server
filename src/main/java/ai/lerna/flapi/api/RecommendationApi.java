package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.api.dto.RecommendationCategoryItem;
import ai.lerna.flapi.service.actionML.dto.EngineConfig;
import ai.lerna.flapi.api.dto.RecommendationEngineRequest;
import ai.lerna.flapi.api.dto.RecommendationEvent;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(RecommendationApi.path)
@Tag(name = RecommendationApi.tag)
public interface RecommendationApi {
	String path = "/api/v2/recommendation";
	String tag = "Lerna Recommendation API";

	@Operation(summary = "Create engine")
	@GetMapping("/create")
	ResponseEntity<EventResponse> createEngine(@RequestParam(value = "token") String token, @RequestParam(value = "engine") String engineId) throws Exception;

	@Operation(summary = "Create engine")
	@PostMapping("/create")
	ResponseEntity<EventResponse> createEngine(@RequestParam(value = "token") String token, @RequestBody RecommendationEngineRequest engineRequest) throws Exception;

	@Operation(summary = "Update engine")
	@GetMapping("/update")
	ResponseEntity<EventResponse> updateEngine(@RequestParam(value = "token") String token, @RequestParam(value = "engine") String engineId) throws Exception;

	@Operation(summary = "Update engine")
	@PostMapping("/update")
	ResponseEntity<EventResponse> updateEngine(@RequestParam(value = "token") String token, @RequestBody RecommendationEngineRequest engineRequest) throws Exception;

	@Operation(summary = "Delete engine")
	@GetMapping("/delete")
	ResponseEntity<EventResponse> deleteEngine(@RequestParam(value = "token") String token, @RequestParam(value = "engine") String engineId) throws Exception;

	@Operation(summary = "Train engine")
	@GetMapping("/train")
	ResponseEntity<EventResponse> trainEngine(@RequestParam(value = "token") String token, @RequestParam(value = "engine") String engineId) throws Exception;

	@Operation(summary = "Cancel training job")
	@GetMapping("/cancelJob")
	ResponseEntity<EventResponse> cancelTrainEngine(@RequestParam(value = "token") String token, @RequestParam(value = "engine") String engineId, @RequestParam(value = "job") String jobId) throws Exception;

	@Operation(summary = "Get status of engines")
	@GetMapping("/engines")
	ResponseEntity<List<EngineConfig>> getEnginesStatus(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Get status of specific engine")
	@GetMapping("/engine")
	ResponseEntity<EngineConfig> getEngineStatus(@RequestParam(value = "token") String token, @RequestParam(value = "engine") String engineId) throws Exception;

	/**
	 * @deprecated Only for SDK version 0.0.7 that already published and distributed
	 */
	@Operation(summary = "Submit Event")
	@PostMapping("/events")
	@Deprecated
	ResponseEntity<EventResponse> submitEvents(@RequestParam(value = "token") String token, @RequestBody RecommendationEvent event) throws Exception;

	@Operation(summary = "Submit Event")
	@PostMapping("/event")
	ResponseEntity<EventResponse> submitEvent(@RequestParam(value = "token") String token, @RequestBody RecommendationEvent event) throws Exception;

	@Operation(summary = "Submit Item")
	@PostMapping("/item")
	ResponseEntity<EventResponse> submitItem(@RequestParam(value = "token") String token, @RequestBody RecommendationCategoryItem item) throws Exception;

	@Operation(summary = "Query items by user ID")
	@GetMapping("/queries/user")
	ResponseEntity<RecommendationItems> getItemsByUser(@RequestParam(value = "token") String token, @RequestParam(value = "user") String user, @RequestParam(value = "engine") String engineId) throws Exception;

	@Operation(summary = "Query items by number")
	@GetMapping("/queries/num")
	ResponseEntity<RecommendationItems> getItemsByNum(@RequestParam(value = "token") String token, @RequestParam(value = "num") Integer num, @RequestParam(value = "engine") String engineId) throws Exception;

	@Operation(summary = "Query items")
	@PostMapping("/queries")
	ResponseEntity<RecommendationItems> getItems(@RequestParam(value = "token") String token, @RequestBody Item item) throws Exception;
}
