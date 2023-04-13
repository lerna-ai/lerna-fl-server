package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.Success;
import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingInitialize;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsRequestV2;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsResponseV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(TrainingApiV2.path)
@Tag(name = TrainingApiV2.tag)
public interface TrainingApiV2 {
	String path = "/api/v2/training";
	String tag = "Training API V2";

	@Operation(summary = "Get new training info")
	@GetMapping("/new")
	TrainingTaskResponse getNewTraining(@RequestParam(value = "token") String token, @RequestParam(value = "deviceId") Long deviceId) throws Exception;

	@Operation(summary = "Get new training info")
	@PostMapping("/new")
	TrainingTaskResponse postNewTraining(@RequestParam(value = "token") String token, @RequestParam(value = "deviceId") Long deviceId, @RequestBody TrainingInitialize trainingInitialize) throws Exception;

	@PostMapping("/submitWeights")
	void postDeviceWeights(@RequestParam(value = "token") String token, @RequestBody TrainingWeightsRequestV2 trainingWeightsRequest) throws Exception;

	@GetMapping("/getNewWeights")
	TrainingWeightsResponseV2 getGlobalWeights(@RequestParam(value = "token") String token, @RequestParam(value = "version") long version) throws Exception;

	@Operation(summary = "Post accuracy")
	@PostMapping("/accuracy")
	void postAccuracy(@RequestParam(value = "token") String token, @RequestBody TrainingAccuracyRequest request) throws Exception;

	@Operation(summary = "Post Inference")
	@PostMapping("/inference")
	void postInference(@RequestParam(value = "token") String token, @RequestBody TrainingInferenceRequest request) throws Exception;

	@Operation(summary = "Post Success")
	@PostMapping("/success")
	void postSuccess(@RequestParam(value = "token") String token, @RequestBody Success success) throws Exception;
}
