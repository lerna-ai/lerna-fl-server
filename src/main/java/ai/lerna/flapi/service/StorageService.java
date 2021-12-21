package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;

import java.util.List;
import java.util.Optional;

public interface StorageService {
	Optional<TrainingTaskResponse> getTask(String token);
	void putTask(String token, TrainingTaskResponse trainingTask);
	Optional<TrainingWeightsResponse> getWeights(String token);
	void putWeights(String token, TrainingWeightsResponse trainingWeights);

	void putDeviceIdToDropTables(List<TrainingTask> trainingTasks, Long deviceId);
}
