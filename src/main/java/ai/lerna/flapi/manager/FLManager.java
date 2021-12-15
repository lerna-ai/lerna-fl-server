package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingInitResponse;

public interface FLManager {
	TrainingInitResponse getNewTraining(String token);
}
