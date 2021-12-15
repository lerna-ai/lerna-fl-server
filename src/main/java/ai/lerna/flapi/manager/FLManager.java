package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingInitResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;

public interface FLManager {
	TrainingInitResponse getNewTraining(String token);
        TrainingWeightsResponse getAccuracy(String token);
}
