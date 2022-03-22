package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.DeviceWeights;
import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaPrediction;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StorageService {

	void activateJob(Long jobId);

	void deactivateJob(Long jobId);

	boolean isJobActive(Long jobId);

	Optional<TrainingTaskResponse> getTask(String token);

	void putTask(String token, TrainingTaskResponse trainingTask);

	Optional<TrainingWeightsResponse> getWeights(String token);

	void putWeights(String token, TrainingWeightsResponse trainingWeights);

	void putDeviceIdToDropTable(List<TrainingTask> trainingTasks, Long deviceId);

	List<Long> getDeviceDropTable(Long jobId);

	int getDeviceWeightsSize(Long jobId);

	void removeDeviceIdFromDropTable(Long jobId, Long deviceId);

	void removeDropTable(Long jobId);

	void removeDeviceWeights(Long jobId);

	boolean existsDeviceIdOnDropTable(long jobId, long deviceId);

	void addDeviceWeights(Long jobId, Long deviceId, Long datapoints, INDArray weights);

	void addDeviceInference(String token, LernaPrediction prediction);

	void addDeviceAccuracy(Long mlId, Long deviceId, Long version, BigDecimal accuracy);

	List<DeviceWeights> getDeviceWeights(Long jobId);

	void deleteDropTable(Long jobId);

	void deleteTaskTable(String token);

	void deleteWeightsTable(String token);

	boolean isTaskVersionActive(String token, long version);

	void persistOnRedis();

	void retrieveFromRedis();

	void cleanupRedis();
}
