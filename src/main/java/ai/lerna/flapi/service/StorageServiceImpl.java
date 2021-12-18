package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
	Map<String, TrainingTaskResponse> tasks = new HashMap<>();
	Map<Long, TrainingWeightsResponse> weights = new HashMap<>();
	@Override
	public Optional<TrainingTaskResponse> getTask(String token) {
		return Optional.ofNullable(tasks.get(token));
	}

	@Override
	public void putTask(String token, TrainingTaskResponse trainingTask) {
		tasks.putIfAbsent(token, trainingTask);
	}
	
	public Optional<TrainingWeightsResponse> getWeights(long jobID) {
		return Optional.ofNullable(weights.get(jobID));
	}

	@Override
	public void putWeights(long jobID, TrainingWeightsResponse trainingWeights) {
		weights.putIfAbsent(jobID, trainingWeights);
	}
}
