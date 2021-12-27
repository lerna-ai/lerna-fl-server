package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaPrediction;
import com.sun.tools.javac.util.Pair;
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

	boolean existsDeviceIdOnDropTable(long jobId, long deviceId);

	void addDeviceWeights(Long jobId, Long deviceId, Long datapoints, INDArray weights);

	void addDeviceInference(String token, LernaPrediction prediction);

	void addDeviceAccuracy(Long ml_id, Long deviceId, Long version, BigDecimal accuracy);

	List<Pair<Long, INDArray>> getDeviceWeights(Long jobId);
	
	void deleteDropTable(Long jobId);
	
	void deleteTaskTable(String token);
	
	void deleteWeightsTable(String token);
}
