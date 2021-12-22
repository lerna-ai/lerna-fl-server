package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.Optional;

public interface StorageService {
	Optional<TrainingTaskResponse> getTask(String token);

	void putTask(String token, TrainingTaskResponse trainingTask);

	Optional<TrainingWeightsResponse> getWeights(String token);

	void putWeights(String token, TrainingWeightsResponse trainingWeights);

	void putDeviceIdToDropTables(List<TrainingTask> trainingTasks, Long deviceId);

	void removeDeviceIdFromDropTables(Long jobId, Long deviceId);

	boolean existsDeviceIdOnDropTable(long jobId, long deviceId);

	void addDeviceWeights(Long jobId, Long deviceId, INDArray weights);
}
