package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
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
	 * Pending devices, per ml and jobid
	 * 1st Dimension ML ID
	 * 2nd Dimension Job ID
	 */
	Map<Long, Map<Long, List<Long>>> pendingDevices = new HashMap<>();
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
		trainingTasks.forEach(trainingTask -> {
			if (!pendingDevices.containsKey(trainingTask.getMlId())) {
				pendingDevices.put(trainingTask.getMlId(), new HashMap<>());
			}
			trainingTask.getJobIds().forEach((model, jobId) -> {
				if (!pendingDevices.get(trainingTask.getMlId()).containsKey(jobId)) {
					pendingDevices.get(trainingTask.getMlId()).put(jobId, new ArrayList<>());
				}
				if (!pendingDevices.get(trainingTask.getMlId()).get(jobId).contains(deviceId)) {
					pendingDevices.get(trainingTask.getMlId()).get(jobId).add(deviceId);
				}
			});
		});
	}


}
