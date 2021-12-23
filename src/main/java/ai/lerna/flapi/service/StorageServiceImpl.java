package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaPredictions;
import com.sun.tools.javac.util.Pair; //Maybe some other library for Pair? Maybe create our own class (yikes)?
import java.math.BigDecimal;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
	
	//Maybe avoid any shared memory locally and put that on Redis...
	
	Map<String, TrainingTaskResponse> tasks = new HashMap<>();
	Map<String, TrainingWeightsResponse> weights = new HashMap<>();
	Map<Long, Map<Long, Pair<Long, INDArray>>> deviceWeights = new HashMap<>();
	Map<String, List<LernaPredictions>> predictions = new HashMap<>();
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
	public void putDeviceIdToDropTable(List<TrainingTask> trainingTasks, Long deviceId) {
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
		if(!deviceWeights.containsKey(jobId)){
			deviceWeights.put(jobId, new HashMap<>());
		}
		deviceWeights.get(jobId).put(deviceId, new Pair<Long, INDArray>(datapoints, weights));
	}
	
	@Override
	public List<Pair<Long, INDArray>> getDeviceWeights(Long jobId) {
		if(!deviceWeights.containsKey(jobId))
			return null;
		else
			return new ArrayList<>(deviceWeights.get(jobId).values());
	}

	@Override
	public void addDeviceInference(String token, Long ml_id, Long deviceId, Long version, String model, String prediction) {
		//connect to postgress to store the predictions
		//add to new table:
		//Inference_history(ml_id, model, deviceId, prediction, version)
		if(!predictions.containsKey(token))
			predictions.put(token, new ArrayList<>());
		predictions.get(token).add(LernaPredictions.newBuilder()
			.setDeviceId(deviceId)
			.setMLId(ml_id)
			.setModel(model)
			.setVersion(version)
			.setPrediction(prediction)
			.setTimestamp(new Date())
			.build());
	}

	@Override
	public List<Long> getDeviceDropTable(Long jobId) {
		return pendingDevices.get(jobId);
	}
	
	@Override
	public int getDeviceWeightsSize(Long jobId) {
		if(!deviceWeights.containsKey(jobId))
			return 0;
		else
			return deviceWeights.get(jobId).size();
	}

	@Override
	public void addDeviceAccuracy(Long ml_id, Long deviceId, Long version, BigDecimal accuracy) {
		
	}

}
