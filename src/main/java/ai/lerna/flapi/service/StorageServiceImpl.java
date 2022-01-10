package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.DeviceWeights;
import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaPrediction;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {

	//Maybe avoid any shared memory locally and put that on Redis...

	Map<Long, Boolean> activeJob = new HashMap<>();
	Map<String, TrainingTaskResponse> tasks = new HashMap<>();
	Map<String, TrainingWeightsResponse> weights = new HashMap<>();
	Map<Long, Map<Long, DeviceWeights>> deviceWeights = new HashMap<>();
	Map<String, List<LernaPrediction>> predictions = new HashMap<>();
	/**
	 * Pending devices as Map of Job ID and list of device IDs
	 */
	Map<Long, List<Long>> pendingDevices = new HashMap<>();

	@Override
	public void activateJob(Long jobId) {
		activeJob.put(jobId, true);
	}

	@Override
	public void deactivateJob(Long jobId) {
		activeJob.remove(jobId);
	}

	@Override
	public boolean isJobActive(Long jobId) {
		return Optional.ofNullable(activeJob.get(jobId)).orElse(false);
	}

	@Override
	public Optional<TrainingTaskResponse> getTask(String token) {
		TrainingTaskResponse response = tasks.get(token);
		// ToDo: Should be validate this logic: if any of jobs is deactivated then not accept new users
		if (Objects.isNull(response) || !response.getTrainingTasks().stream()
			.flatMap(task -> task.getJobIds().values().stream())
			.allMatch(this::isJobActive)) {
			return Optional.empty();
		}
		return Optional.of(response);
	}

	@Override
	public void putTask(String token, TrainingTaskResponse trainingTask) {
		tasks.put(token, trainingTask);
		trainingTask.getTrainingTasks().stream()
			.flatMap(task -> task.getJobIds().values().stream())
			.forEach(this::activateJob);
	}

	@Override
	public Optional<TrainingWeightsResponse> getWeights(String token) {
		return Optional.ofNullable(weights.get(token));
	}

	@Override
	public void putWeights(String token, TrainingWeightsResponse trainingWeights) {
		weights.put(token, trainingWeights);
	}

	@Override
	public void putDeviceIdToDropTable(List<TrainingTask> trainingTasks, Long deviceId) {
		trainingTasks.stream()
			.flatMap(trainingTask -> trainingTask.getJobIds().values().stream())
			.filter(this::isJobActive)
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
	public void removeDeviceIdFromDropTable(Long jobId, Long deviceId) {
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
	public void addDeviceWeights(Long jobId, Long deviceId, Long datapoints, INDArray weights) {
		if (!deviceWeights.containsKey(jobId)) {
			deviceWeights.put(jobId, new HashMap<>());
		}
		deviceWeights.get(jobId).put(deviceId, DeviceWeights.newBuilder().setDataPoints(datapoints).setWeights(weights).build());
	}

	@Override
	public List<DeviceWeights> getDeviceWeights(Long jobId) {
		if (!deviceWeights.containsKey(jobId))
			return null;
		else
			return new ArrayList<>(deviceWeights.get(jobId).values());
	}

	@Override
	public void addDeviceInference(String token, LernaPrediction prediction) {
		if (!predictions.containsKey(token)) {
			predictions.put(token, new ArrayList<>());
		}
		predictions.get(token).add(prediction);
	}

	@Override
	public List<Long> getDeviceDropTable(Long jobId) {
		return pendingDevices.get(jobId);
	}

	@Override
	public int getDeviceWeightsSize(Long jobId) {
		if (!deviceWeights.containsKey(jobId)) {
			return 0;
		}
		return deviceWeights.get(jobId).size();
	}

	@Override
	public void addDeviceAccuracy(Long ml_id, Long deviceId, Long version, BigDecimal accuracy) {
		//Store the accuracy
	}

	@Override
	public void deleteDropTable(Long jobId) {
		pendingDevices.remove(jobId);
	}

	@Override
	public void deleteTaskTable(String token) {
		tasks.remove(token);
	}

	@Override
	public void deleteWeightsTable(String token) {
		weights.remove(token);
	}

	@Override
	public boolean isTaskVersionActive(String token, long version) {
		return tasks.containsKey(token)
				&& tasks.get(token).getVersion().equals(version);
	}

}
