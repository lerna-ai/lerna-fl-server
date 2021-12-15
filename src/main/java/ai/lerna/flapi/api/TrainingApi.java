package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.TrainingInitResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(TrainingApi.path)
@Tag(name = TrainingApi.tag)
public interface TrainingApi {
	String path = "/api/v1/training";
	String tag = "Training API";

	@Operation(summary = "Get new training info")
	@GetMapping("/new")
	TrainingInitResponse getNewTraining(@RequestParam(value = "token") String token) throws Exception;
}
