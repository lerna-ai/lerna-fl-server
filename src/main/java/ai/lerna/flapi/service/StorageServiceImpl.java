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
import org.nd4j.linalg.api.ndarray.INDArray;

@Service
public class StorageServiceImpl implements StorageService {
	Map<String, TrainingTaskResponse> tasks = new HashMap<>();
	Map<String, TrainingWeightsResponse> weights = new HashMap<>();

	/**
	 * Pending devices, per ml and jobid
	 * 1st Dimension ML ID
	 * 2nd Dimension Job ID
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
		trainingTasks.forEach(trainingTask -> {
			trainingTask.getJobIds().forEach((model, jobId) -> {
				if (!pendingDevices.containsKey(jobId)) {
					pendingDevices.put(jobId, new ArrayList<>());
				}
				if (!pendingDevices.get(jobId).contains(deviceId)) {
					pendingDevices.get(jobId).add(deviceId);
				}
			});
		});
	}

	@Override
	public void removeDeviceIdFromDropTables(Long jobId, Long deviceId) {
		if (pendingDevices.containsKey(jobId)) {
			if (pendingDevices.get(jobId).contains(deviceId)){
				pendingDevices.get(jobId).remove(deviceId);
//				if(pendingDevices.get(jobId).isEmpty())
//					pendingDevices.remove(jobId);
			}
				
		}
	}

	@Override
	public void addDeviceWeights(Long jobId, Long deviceId, INDArray weights) {
		//connect to redis and/or memory to store the weights
		//keep counter per jobId in order to determine how many devices were gathered
	}

	

}
