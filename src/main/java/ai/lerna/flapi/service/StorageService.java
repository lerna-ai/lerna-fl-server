package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import com.sun.tools.javac.util.Pair;
import java.math.BigDecimal;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.List;
import java.util.Optional;

public interface StorageService {
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
	
	void addDeviceInference(String token, Long ml_id, Long deviceId, Long version, String model, String prediction);
	
	void addDeviceAccuracy(Long ml_id, Long deviceId, Long version, BigDecimal accuracy);
	
	List<Pair<Long, INDArray>> getDeviceWeights(Long jobId);
}
