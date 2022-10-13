package ai.lerna.flapi.api;

import ai.lerna.flapi.api.converter.TrainingConverter;
import ai.lerna.flapi.api.dto.Success;
import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsRequestV2;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsResponseV2;
import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class TrainingApiV2Impl implements TrainingApiV2 {

	private FLManager flManager;
	private TrainingApiValidator validator;
	private TrainingConverter converter;
	private TrainingApi trainingApi;

	@Autowired
	public TrainingApiV2Impl(FLManager flManager, TrainingApiValidator validator, TrainingConverter converter, TrainingApi trainingApi) {
		this.flManager = flManager;
		this.validator = validator;
		this.converter = converter;
		this.trainingApi = trainingApi;
	}

	@Override
	public TrainingTaskResponse getNewTraining(@RequestParam(value = "token") String token, Long deviceId) throws Exception {
		return trainingApi.getNewTraining(token, deviceId);
	}

	@Override
	public void postDeviceWeights(String token, TrainingWeightsRequestV2 trainingWeightsRequest) throws Exception {
		validator.tokenValidation(token);
		flManager.saveDeviceWeights(token, converter.convertTrainingWeightsRequest.apply(trainingWeightsRequest));
	}

	public TrainingWeightsResponseV2 getGlobalWeights(String token, long version) throws Exception {
		validator.tokenValidation(token);
		TrainingWeightsResponse trainingWeightsResponse = flManager.getGlobalWeights(token, version);
		if (Objects.isNull(trainingWeightsResponse)) {
			return null;
		}
		return converter.convertTrainingWeightsResponseV2.apply(trainingWeightsResponse);
	}

	@Override
	public void postAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest) throws Exception {
		trainingApi.postAccuracy(token, trainingAccuracyRequest);
	}

	@Override
	public void postInference(String token, TrainingInferenceRequest trainingInferenceRequest) throws Exception {
		trainingApi.postInference(token, trainingInferenceRequest);
	}

	@Override
	public void postSuccess(String token, Success success) throws Exception {
		trainingApi.postSuccess(token, success);
	}
}
