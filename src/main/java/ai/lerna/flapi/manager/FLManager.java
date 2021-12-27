package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;

public interface FLManager {

    TrainingTaskResponse getNewTraining(String token, Long deviceId);

    String saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) throws Exception;

    TrainingWeightsResponse getGlobalWeights(String token, long version);

    String saveDeviceAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest);
    
    String saveInference(String token, TrainingInferenceRequest trainingInferenceRequest);
}
