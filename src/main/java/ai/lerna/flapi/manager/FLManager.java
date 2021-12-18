package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;

public interface FLManager {

    TrainingTaskResponse getNewTraining(String token);

    String saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest);

    TrainingWeightsResponse getGlobalWeights(String token, long jobId);

    String saveDeviceAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest);
}
