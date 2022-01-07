package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(TrainingApi.path)
@Tag(name = TrainingApi.tag)
public interface TrainingApi {
	String path = "/api/v1/training";
	String tag = "Training API";

	@Operation(summary = "Get new training info")
	@GetMapping("/new")
	TrainingTaskResponse getNewTraining(@RequestParam(value = "token") String token, @RequestParam(value = "deviceId") Long deviceId) throws Exception;

	@PostMapping("/submitWeights")
	String postDeviceWeights(@RequestParam(value = "token") String token, @RequestBody TrainingWeightsRequest trainingWeightsRequest) throws Exception;

	@GetMapping("/getNewWeights")
	TrainingWeightsResponse getGlobalWeights(@RequestParam(value = "token") String token, @RequestParam(value = "version") long version) throws Exception;

	@Operation(summary = "Post accuracy")
	@PostMapping("/accuracy")
	void postAccuracy(@RequestParam(value = "token") String token, @RequestBody TrainingAccuracyRequest request) throws Exception;

	@Operation(summary = "Post Inference")
	@PostMapping("/inference")
	String postInference(@RequestParam(value = "token") String token, @RequestBody TrainingInferenceRequest request) throws Exception;
}
