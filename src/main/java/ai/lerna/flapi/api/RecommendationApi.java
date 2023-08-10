package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(RecommendationApi.path)
@CrossOrigin
@Tag(name = RecommendationApi.tag)
public interface RecommendationApi {
	String path = "/api/v2/recommendation";
	String tag = "Lerna Recommendation API";

	@Operation(summary = "Submit Event")
	@PostMapping("/events")
	ResponseEntity<EventResponse> submitEvent(@RequestParam(value = "token") String token, @RequestBody Event event) throws Exception;

	@Operation(summary = "Query items by user ID")
	@GetMapping("/queries/user")
	ResponseEntity<RecommendationItems> getItemsByUser(@RequestParam(value = "token") String token, @RequestParam(value = "user") String user) throws Exception;

	@Operation(summary = "Query items by number")
	@GetMapping("/queries/num")
	ResponseEntity<RecommendationItems> getItemsByNum(@RequestParam(value = "token") String token, @RequestParam(value = "num") Integer num) throws Exception;

	@Operation(summary = "Query items")
	@PostMapping("/queries")
	ResponseEntity<RecommendationItems> getItems(@RequestParam(value = "token") String token, @RequestBody Item item) throws Exception;
}
