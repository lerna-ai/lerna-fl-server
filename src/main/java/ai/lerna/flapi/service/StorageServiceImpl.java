package ai.lerna.flapi.service;

import ai.lerna.flapi.api.dto.DeviceWeights;
import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.config.JacksonConfiguration;
import ai.lerna.flapi.entity.LernaPrediction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StorageServiceImpl implements StorageService {

	private static final String KEY_PREFIX = "Lerna:FL:";
	private static final String KEY_DEBUG_PREFIX = KEY_PREFIX + "Debug:";
	private static final String KEY_ACTIVE_JOB = "ActiveJob";
	private static final String KEY_TASKS = "Tasks";
	private static final String KEY_WEIGHTS = "Weights";
	private static final String KEY_DEVICE_WEIGHTS = "DeviceWeights";
	private static final String KEY_PREDICTIONS = "Predictions";
	private static final String KEY_PENDING_DEVICES = "PendingDevices";
	RedisTemplate<String, Object> redisTemplate;
	ValueOperations<String, Object> redisOps;
	protected final static ObjectMapper mapper = JacksonConfiguration.getNewObjectMapper();

	@Autowired
	public StorageServiceImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisOps = this.redisTemplate.opsForValue();
	}

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
					if (deviceWeights.containsKey(jobId))
						if(deviceWeights.get(jobId).containsKey(deviceId))
							deviceWeights.get(jobId).remove(deviceId);
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
	public void removeDropTable(Long jobId) {
		if (pendingDevices.containsKey(jobId)) {
			pendingDevices.remove(jobId);
		}
	}

	@Override
	public void removeDeviceWeights(Long jobId) {
		if (deviceWeights.containsKey(jobId)) {
			deviceWeights.remove(jobId);
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
	public void addDeviceAccuracy(Long mlId, Long deviceId, Long version, BigDecimal accuracy) {
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

	@Override
	@PreDestroy
	public void persistOnRedis() {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Persist FL configuration on Redis");
		redisOps.set(KEY_PREFIX + KEY_ACTIVE_JOB, activeJob);
		redisOps.set(KEY_PREFIX + KEY_TASKS, tasks);
		redisOps.set(KEY_PREFIX + KEY_WEIGHTS, weights);
		redisOps.set(KEY_PREFIX + KEY_DEVICE_WEIGHTS, deviceWeights);
		//redisOps.set(KEY_PREFIX + KEY_PREDICTIONS, predictions);
		redisOps.set(KEY_PREFIX + KEY_PENDING_DEVICES, pendingDevices);
		try {
			redisOps.set(KEY_DEBUG_PREFIX + KEY_ACTIVE_JOB, mapper.writeValueAsString(activeJob));
			redisOps.set(KEY_DEBUG_PREFIX + KEY_TASKS, mapper.writeValueAsString(tasks));
			redisOps.set(KEY_DEBUG_PREFIX + KEY_WEIGHTS, mapper.writeValueAsString(weights));
			redisOps.set(KEY_DEBUG_PREFIX + KEY_DEVICE_WEIGHTS, mapper.writeValueAsString(deviceWeights));
			//redisOps.set(KEY_DEBUG_PREFIX + KEY_PREDICTIONS, mapper.writeValueAsString(predictions));
			redisOps.set(KEY_DEBUG_PREFIX + KEY_PENDING_DEVICES, mapper.writeValueAsString(pendingDevices));
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot persist on redis debug storage", e);
		}
	}

	@Override
	public void retrieveFromRedis() {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Retrieve FL configuration from Redis");
		try {
			activeJob = (Map<Long, Boolean>) Optional.ofNullable(redisOps.get(KEY_PREFIX + KEY_ACTIVE_JOB)).orElse(new HashMap<>());
		} catch (Exception e) {
			try {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Can't retrieve active jobs from Redis, try to retrieve it from json object. " + e.getMessage());
				activeJob = mapper.readValue((String) redisOps.get(KEY_DEBUG_PREFIX + KEY_ACTIVE_JOB), new TypeReference<Map<Long, Boolean>>() {
				});
			} catch (JsonProcessingException ex) {
				activeJob = new HashMap<>();
			}
		}

		try {
			tasks = (Map<String, TrainingTaskResponse>) Optional.ofNullable(redisOps.get(KEY_PREFIX + KEY_TASKS)).orElse(new HashMap<>());
		} catch (Exception e) {
			try {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Can't retrieve tasks from Redis, try to retrieve it from json object. " + e.getMessage());
				tasks = mapper.readValue((String) redisOps.get(KEY_DEBUG_PREFIX + KEY_TASKS), new TypeReference<Map<String, TrainingTaskResponse>>() {
				});
			} catch (JsonProcessingException ex) {
				tasks = new HashMap<>();
			}
		}

		try {
			weights = (Map<String, TrainingWeightsResponse>) Optional.ofNullable(redisOps.get(KEY_PREFIX + KEY_WEIGHTS)).orElse(new HashMap<>());
		} catch (Exception e) {
			try {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Can't retrieve weights from Redis, try to retrieve it from json object. " + e.getMessage());
				weights = mapper.readValue((String) redisOps.get(KEY_DEBUG_PREFIX + KEY_WEIGHTS), new TypeReference<Map<String, TrainingWeightsResponse>>() {
				});
			} catch (JsonProcessingException ex) {
				weights = new HashMap<>();
			}
		}

		try {
			deviceWeights = (Map<Long, Map<Long, DeviceWeights>>) Optional.ofNullable(redisOps.get(KEY_PREFIX + KEY_DEVICE_WEIGHTS)).orElse(new HashMap<>());
		} catch (Exception e) {
			try {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Can't retrieve device weights from Redis, try to retrieve it from json object. " + e.getMessage());
				deviceWeights = mapper.readValue((String) redisOps.get(KEY_DEBUG_PREFIX + KEY_DEVICE_WEIGHTS), new TypeReference<Map<Long, Map<Long, DeviceWeights>>>() {
				});
			} catch (JsonProcessingException ex) {
				deviceWeights = new HashMap<>();
			}
		}

		//predictions = (Map<String, List<LernaPrediction>>) Optional.ofNullable(redisOps.get(KEY_PREFIX + KEY_PREDICTIONS)).orElse(new HashMap<>());

		try {
			pendingDevices = (Map<Long, List<Long>>) Optional.ofNullable(redisOps.get(KEY_PREFIX + KEY_PENDING_DEVICES)).orElse(new HashMap<>());
		} catch (Exception e) {
			try {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Can't retrieve pending devices from Redis, try to retrieve it from json object. " + e.getMessage());
				pendingDevices = mapper.readValue((String) redisOps.get(KEY_DEBUG_PREFIX + KEY_PENDING_DEVICES), new TypeReference<Map<Long, List<Long>>>() {
				});
			} catch (JsonProcessingException ex) {
				pendingDevices = new HashMap<>();
			}
		}
	}

	public void cleanupRedis() {
		redisTemplate.delete(KEY_PREFIX + KEY_ACTIVE_JOB);
		redisTemplate.delete(KEY_PREFIX + KEY_TASKS);
		redisTemplate.delete(KEY_PREFIX + KEY_WEIGHTS);
		redisTemplate.delete(KEY_PREFIX + KEY_DEVICE_WEIGHTS);
		redisTemplate.delete(KEY_PREFIX + KEY_PREDICTIONS);
		redisTemplate.delete(KEY_PREFIX + KEY_PENDING_DEVICES);
		redisTemplate.delete(KEY_DEBUG_PREFIX + KEY_ACTIVE_JOB);
		redisTemplate.delete(KEY_DEBUG_PREFIX + KEY_TASKS);
		redisTemplate.delete(KEY_DEBUG_PREFIX + KEY_WEIGHTS);
		redisTemplate.delete(KEY_DEBUG_PREFIX + KEY_DEVICE_WEIGHTS);
		redisTemplate.delete(KEY_DEBUG_PREFIX + KEY_PREDICTIONS);
		redisTemplate.delete(KEY_DEBUG_PREFIX + KEY_PENDING_DEVICES);
	}
}
