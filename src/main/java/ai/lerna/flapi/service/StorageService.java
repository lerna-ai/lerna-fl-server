package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;

import java.util.Optional;

public interface StorageService {
	Optional<TrainingTaskResponse> getTask(String token);
	void putTask(String token, TrainingTaskResponse trainingTask);
	Optional<TrainingWeightsResponse> getWeights(long jobID);
	void putWeights(long jobID, TrainingWeightsResponse trainingWeights);
}
