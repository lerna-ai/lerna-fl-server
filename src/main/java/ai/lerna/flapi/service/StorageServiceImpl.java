package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
	Map<String, TrainingTaskResponse> tasks = new HashMap<>();
	Map<String, TrainingWeightsResponse> weights = new HashMap<>();

	/**
	 * Pending devices as Map of Job ID and list of device IDs
	 */
	Map<Long, List<Long>> pendingDevices = new HashMap<>();

	@Override
	public Optional<TrainingTaskResponse> getTask(String token) {
		return Optional.ofNullable(tasks.get(token));
	}

	@Override
	public void putTask(String token, TrainingTaskResponse trainingTask) {
		tasks.putIfAbsent(token, trainingTask);
	}

	@Override
	public Optional<TrainingWeightsResponse> getWeights(String token) {
		return Optional.ofNullable(weights.get(token));
	}

	@Override
	public void putWeights(String token, TrainingWeightsResponse trainingWeights) {
		weights.putIfAbsent(token, trainingWeights);
	}

	@Override
	public void putDeviceIdToDropTables(List<TrainingTask> trainingTasks, Long deviceId) {
		trainingTasks.stream()
			.flatMap(trainingTask -> trainingTask.getJobIds().values().stream())
			.forEach(jobId -> {
				if (!pendingDevices.containsKey(jobId)) {
					pendingDevices.put(jobId, new ArrayList<>());
				}
				// ToDo: Maybe we need to check what happens if device already exists on pending devices
				if (!pendingDevices.get(jobId).contains(deviceId)) {
					pendingDevices.get(jobId).add(deviceId);
				}
			});
	}

	@Override
	public void removeDeviceIdFromDropTables(Long jobId, Long deviceId) {
		if (pendingDevices.containsKey(jobId)) {
			pendingDevices.get(jobId).remove(deviceId);
		}
	}

	@Override
	public boolean existsDeviceIdOnDropTable(long jobId, long deviceId) {
		return pendingDevices.containsKey(jobId)
			&& pendingDevices.get(jobId).contains(deviceId);
	}

	@Override
	public void addDeviceWeights(Long jobId, Long deviceId, INDArray weights) {
		//connect to redis and/or memory to store the weights
		//keep counter per jobId in order to determine how many devices were gathered
	}

}
