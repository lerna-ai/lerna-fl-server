package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.DeviceWeights;
import ai.lerna.flapi.api.dto.Success;
import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInference;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeights;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.entity.LernaPredictionMetadata;
import ai.lerna.flapi.entity.MLHistory;
import ai.lerna.flapi.entity.MLHistoryDatapoint;
import ai.lerna.flapi.entity.MLHistoryWeights;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.repository.LernaJobRepository;
import ai.lerna.flapi.repository.LernaMLRepository;
import ai.lerna.flapi.repository.LernaPredictionRepository;
import ai.lerna.flapi.repository.MLHistoryDatapointRepository;
import ai.lerna.flapi.repository.MLHistoryRepository;
import ai.lerna.flapi.repository.SuccessRepository;
import ai.lerna.flapi.service.MpcService;
import ai.lerna.flapi.service.StorageService;
import ai.lerna.flapi.service.WebhookService;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FLManagerImpl implements FLManager {

	private final MpcService mpcService;
	private final LernaAppRepository lernaAppRepository;
	private final LernaMLRepository lernaMLRepository;
	private final LernaJobRepository lernaJobRepository;
	private final LernaPredictionRepository lernaPredictionRepository;
	private final SuccessRepository successRepository;
	private final MLHistoryRepository mlHistoryRepository;
	private final MLHistoryDatapointRepository mlHistoryDatapointRepository;
	private final StorageService storageService;
	private final WebhookService webhookService;

	@Value("${app.config.mpcServer.host:localhost}")
	private String mpcHost;

	@Value("${app.config.mpcServer.port:31337}")
	private int mpcPort;

	@Value("${app.config.fl.scalingFactor:100000}")
	private int scalingFactor;

	@Autowired
	public FLManagerImpl(MpcService mpcService, LernaAppRepository lernaAppRepository, LernaMLRepository lernaMLRepository, LernaJobRepository lernaJobRepository, LernaPredictionRepository lernaPredictionRepository, SuccessRepository successRepository, MLHistoryRepository mlHistoryRepository, MLHistoryDatapointRepository mlHistoryDatapointRepository, StorageService storageService, WebhookService webhookService) {
		this.mpcService = mpcService;
		this.lernaAppRepository = lernaAppRepository;
		this.lernaMLRepository = lernaMLRepository;
		this.lernaJobRepository = lernaJobRepository;
		this.lernaPredictionRepository = lernaPredictionRepository;
		this.successRepository = successRepository;
		this.mlHistoryRepository = mlHistoryRepository;
		this.mlHistoryDatapointRepository = mlHistoryDatapointRepository;
		this.storageService = storageService;
		this.webhookService = webhookService;
	}

	@Override
	public TrainingTaskResponse getNewTraining(String token, Long deviceId) throws Exception {
		TrainingTaskResponse trainingTaskResponse = null;
		Optional<TrainingTaskResponse> taskResponse = storageService.getActiveTask(token);
		if (taskResponse.isPresent()) {
			trainingTaskResponse = taskResponse.get();
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting {0} TrainingTasks from cache", trainingTaskResponse.getTrainingTasks().size());
			storageService.putDeviceIdToDropTable(trainingTaskResponse.getTrainingTasks(), deviceId);
		} else {
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "TrainingTasks not found in cache");
		}

		return trainingTaskResponse;
	}

	@Override
	@Transactional
	public void saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) throws Exception {
		if (!storageService.existsDeviceIdOnDropTable(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId())) {
			throw new Exception("Device ID not exists on pending devices list");
		}
		if (!lernaMLRepository.existsByAppToken(token)) {
			throw new Exception("Not exists ML for selected token");
		}
		if (!storageService.isTaskVersionActive(token, trainingWeightsRequest.getVersion())) {
			throw new Exception("Incorrect ML task version for selected token");
		}
		//!!!This jobId should not be the same as the id of the Jobs table, it is the id from the MPC server
		storageService.removeDeviceIdFromDropTable(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId());
		storageService.addDeviceWeights(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId(), trainingWeightsRequest.getDatapoints(), trainingWeightsRequest.getDeviceWeights());
		//checkAndAggregate(token);
	}

	@Override
	public TrainingWeightsResponse getGlobalWeights(String token, long version) {
		// if user provide latest version return null
		TrainingWeightsResponse trainingWeightsResponse;
		Optional<TrainingWeightsResponse> weightResponse = storageService.getWeights(token);
		if (weightResponse.isPresent()) {
			long currentVersion = weightResponse.get().getVersion();
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "In cache - Current version: {0} Phone version: {1}", new Object[]{currentVersion, version});
			trainingWeightsResponse = (currentVersion > version)
					? weightResponse.get()
					: null;
		} else {

			trainingWeightsResponse = createTrainingWeights(token);
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "In DB - Current version: {0} Phone version: {1}", new Object[]{trainingWeightsResponse.getVersion(), version});
			storageService.putWeights(token, trainingWeightsResponse);
			long currentVersion = trainingWeightsResponse.getVersion();
			trainingWeightsResponse = (currentVersion > version)
					? trainingWeightsResponse
					: null;
		}
		return trainingWeightsResponse;
	}

	@Override
	@Transactional
	public void saveDeviceAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest) throws Exception {
		if (!lernaMLRepository.existsByAppTokenAndMlId(token, trainingAccuracyRequest.getMlId())) {
			throw new Exception("Not valid ML id for selected token");
		}
		MLHistory mlHistory = mlHistoryRepository.findByMLIdAndVersion(trainingAccuracyRequest.getMlId(), trainingAccuracyRequest.getVersion())
				.orElseThrow(() -> new ValidationException("Not valid version for selected ML id"));
		MLHistoryDatapoint mlHistoryDatapoint = constructMLHistoryDatapoint(mlHistory.getId(), trainingAccuracyRequest.getDeviceId(), trainingAccuracyRequest.getAccuracy());
		mlHistoryDatapointRepository.save(mlHistoryDatapoint);
		mlHistoryRepository.updateAccuracyAverage(mlHistory.getId());
	}

	private MLHistoryDatapoint constructMLHistoryDatapoint(long historyId, long deviceId, BigDecimal accuracy) {
		MLHistoryDatapoint mlHistoryDatapoint = new MLHistoryDatapoint();
		mlHistoryDatapoint.setHistoryId(historyId);
		mlHistoryDatapoint.setTimestamp(new Date());
		mlHistoryDatapoint.setDeviceId(deviceId);
		mlHistoryDatapoint.setAccuracy(accuracy);
		return mlHistoryDatapoint;
	}

	private TrainingTaskResponse createTrainingTask(String token) throws Exception {
		List<TrainingTask> trainingTasks = new ArrayList<>();
		if (!lernaMLRepository.existsByAppToken(token)) {
			throw new Exception("Not exists ML for selected token");
		}
		lernaMLRepository.findAllByAppToken(token).forEach(lernaML -> {
			Map<String, Long> jobIds = new HashMap<>();
			lernaJobRepository.findByMLId(lernaML.getId()).forEach(lernaJob -> jobIds.put(lernaJob.getPrediction(), mpcService.getLernaJob(mpcHost, mpcPort, lernaML.getPrivacy().getEpsilon(), lernaML.getML().getDimensions(), lernaML.getML().getNormalization()).getCompId()));
			trainingTasks.add(TrainingTask.newBuilder()
					.setJobIds(jobIds)
					.setMlId(lernaML.getId())
					.setMlModel(lernaML.getModel())
					.setLernaMLParameters(lernaML.getML())
					.build());
		});

		return TrainingTaskResponse.newBuilder()
				.setTrainingTasks(trainingTasks)
				.setVersion(lernaAppRepository.getVersionByToken(token).orElse(0L) + 1L)
				.build();
	}

	private TrainingWeightsResponse createTrainingWeights(String token) {
		List<TrainingWeights> trainingWeights = new ArrayList<>();
		lernaMLRepository.findAllByAppToken(token).forEach(lernaML -> {
			Map<String, INDArray> weights = new HashMap<>();
			lernaJobRepository.findByMLId(lernaML.getId()).forEach(lernaJob -> weights.put(lernaJob.getPrediction(), lernaJob.getWeights()));
			trainingWeights.add(TrainingWeights.newBuilder()
					.setMlId(lernaML.getId())
					.setMlName(lernaML.getModel())
					.setAccuracy(lernaML.getAccuracy())
					.setWeights(weights)
					.build());
		});

		return TrainingWeightsResponse.newBuilder()
				.setTrainingWeights(trainingWeights)
				.setVersion(lernaAppRepository.getVersionByToken(token).orElse(0L))
				.build();
	}

	@Override
	public void saveInference(String token, TrainingInferenceRequest trainingInferenceRequest) throws Exception {
		if (!lernaMLRepository.existsByAppToken(token)) {
			throw new Exception("Not exists ML for selected token");
		}
		LernaPredictionMetadata lernaPredictionMetadata = new LernaPredictionMetadata();
		lernaPredictionMetadata.setUserIdentifier(trainingInferenceRequest.getUserIdentifier());
		for (TrainingInference result : trainingInferenceRequest.getTrainingInference()) {
			LernaPrediction lernaPrediction = LernaPrediction.newBuilder()
					.setDeviceId(trainingInferenceRequest.getDeviceId())
					.setMLId(result.getMl_id())
					.setModel(result.getModel())
					.setVersion(trainingInferenceRequest.getVersion())
					.setPrediction(result.getPrediction())
					.setMetadata(lernaPredictionMetadata)
					.setTimestamp(new Date())
					.build();
			lernaPredictionRepository.save(lernaPrediction);
			webhookService.sendPrediction(lernaPrediction);
			//storageService.addDeviceInference(token, lernaPrediction);
		}
	}

	@Override
	public void saveSuccess(String token, Success success) throws Exception {
		if (!lernaMLRepository.existsByAppToken(token)) {
			throw new Exception("Not exists ML for selected token");
		}
		successRepository.save(ai.lerna.flapi.entity.Success.newBuilder()
				.setMLId(success.getMl_id())
				.setVersion(success.getVersion())
				.setDeviceId(success.getDeviceId())
				.setSuccess(success.getSuccess())
				.setPrediction(success.getPrediction())
				.setTimestamp(new Date())
				.build());
	}

	@Override
	@Transactional
	public void checkAndAggregateAll() throws Exception {
		lernaAppRepository.getTokens().forEach(token -> {
			try {
				checkAndAggregate(token);
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	@Override
	@Transactional
	public void checkAndAggregate(String token) throws Exception {
		int numOfUsers = lernaMLRepository.findUsersNumByAppToken(token);
		TrainingTaskResponse trainingTaskResponse = storageService.getTask(token)
				.orElseThrow(() -> new Exception("Not active ML for selected token"));

		if (!trainingTaskResponse.getTrainingTasks().stream()
				.flatMap(trainingTask -> trainingTask.getJobIds().values().stream())
				.allMatch(job -> storageService.getDeviceWeightsSize(job) >= numOfUsers)) {
			return;
		}
		trainingTaskResponse.getTrainingTasks().forEach(task -> {
			long mlId = task.getMlId();
			Set<MLHistoryWeights> historyWeights = new HashSet<>();
			for (Map.Entry<String, Long> job : task.getJobIds().entrySet()) {
				storageService.deactivateJob(job.getValue());
				List<DeviceWeights> weights = storageService.getDeviceWeights(job.getValue());
				if (Objects.isNull(weights)) {
					// Kill jobs from privacy server
					mpcService.getLernaNoise(mpcHost, mpcPort, job.getValue(), new ArrayList<>(storageService.getDeviceDropTable(job.getValue())));
					continue;
				}
				INDArray sum = Nd4j.zeros(weights.get(0).getWeights().rows(), 1);
				long totalPoints = 0;
				for (DeviceWeights w : weights) {
					totalPoints += w.getDataPoints();
					sum = sum.add(w.getWeights());
				}
				List<BigDecimal> shareList = mpcService.getLernaNoise(mpcHost, mpcPort, job.getValue(), new ArrayList<>(storageService.getDeviceDropTable(job.getValue()))).getShare();
				INDArray shares = Nd4j.zeros(weights.get(0).getWeights().rows(), 1);
				for (int k = 0; k < weights.get(0).getWeights().rows(); k++) {
					shares.putScalar(k, shareList.get(k).doubleValue());
				}
				//these are the final weights for this job
				sum = sum.add(shares).mul(1.0 / (storageService.getDeviceWeightsSize(job.getValue()) * scalingFactor));

				//update weights functionality
				INDArray finalSum = sum;
				long finalTotalPoints = totalPoints;
				lernaJobRepository.findByMLIdAndPrediction(mlId, job.getKey())
						.forEach(lernaJob -> {
							lernaJob.setWeights(finalSum);
							lernaJob.setTotalDataPoints(lernaJob.getTotalDatapoints() + finalTotalPoints);
							lernaJob.setTotalDevices(storageService.getDeviceWeightsSize(job.getValue()));
							lernaJobRepository.save(lernaJob);
						});

				// Create weights object for ML History table
				MLHistoryWeights mlHistoryWeights = new MLHistoryWeights();
				mlHistoryWeights.setPrediction(job.getKey());
				mlHistoryWeights.setWeights(finalSum);
				historyWeights.add(mlHistoryWeights);

				storageService.deleteDropTable(job.getValue());
				storageService.removeDeviceWeights(job.getValue());

			}

			// Insert/Update ML History record
			MLHistory mlHistory = mlHistoryRepository.findByMLIdAndVersion(mlId, trainingTaskResponse.getVersion()).orElse(new MLHistory());
			mlHistory.setMLId(mlId);
			mlHistory.setVersion(trainingTaskResponse.getVersion());
			mlHistory.setWeights(historyWeights);
			mlHistoryRepository.save(mlHistory);
			lernaMLRepository.updateAccuracy(mlId);
		});

		lernaAppRepository.incrementVersionByToken(token);
		storageService.deleteTaskTable(token);
		storageService.deleteWeightsTable(token);
		//create next trainingtask
		TrainingTaskResponse newTrainingTaskResponse = createTrainingTask(token);
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting {0} TrainingTasks from db", newTrainingTaskResponse.getTrainingTasks().size());
		storageService.putTask(token, newTrainingTaskResponse);
	}

	@Override
	public void startup() throws Exception {
		storageService.retrieveFromRedis();
		prepareTrainingTasks();
	}

	public void prepareTrainingTasks() throws Exception {
		lernaAppRepository.findAll().stream()
				.filter(lernaApp -> !storageService.getTask(lernaApp.getToken()).isPresent())
				.forEach(lernaApp -> prepareTrainingTask(lernaApp.getToken()));
	}

	private void prepareTrainingTask(String token) {
		try {
			TrainingTaskResponse newTrainingTaskResponse = createTrainingTask(token);
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting {0} TrainingTasks from db for token {1}", new Object[]{newTrainingTaskResponse.getTrainingTasks().size(), token});
			storageService.putTask(token, newTrainingTaskResponse);
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void replaceAllJobs() throws Exception {
		lernaAppRepository.findAll().stream()
				.filter(lernaApp -> storageService.getTask(lernaApp.getToken()).isPresent())
				.forEach(lernaApp -> replaceJobs(lernaApp.getToken()));
	}

	public void replaceJobs(String token) {
		storageService.getTask(token).ifPresent(trainingTaskResponse -> {
			trainingTaskResponse.getTrainingTasks().forEach(trainingTask -> {
				trainingTask.getJobIds().forEach((prediction, jobId) -> {
					// Kill jobs from privacy server
					try {
						mpcService.getLernaNoise(mpcHost, mpcPort, jobId, new ArrayList<>(Optional.ofNullable(storageService.getDeviceDropTable(jobId)).orElse(new ArrayList<>())));
					} catch (Exception e) {
						Logger.getLogger(this.getClass().getName()).log(Level.WARNING, String.format("Cannot kill job on privacy server %s", e.getMessage()));
					}
					// Remove jobs related data from storage service
					storageService.deactivateJob(jobId);
					storageService.removeDropTable(jobId);
					storageService.removeDeviceWeights(jobId);
				});
				storageService.deleteTaskTable(token);
				prepareTrainingTask(token);
			});
		});
	}

	@Override
	public void cleanupGlobalWeights(String token) throws Exception {
		if (storageService.getWeights(token).isPresent()) {
			storageService.deleteWeightsTable(token);
		}
	}

	@Override
	public void cleanupDeviceWeights(String token) throws Exception {
		TrainingTaskResponse trainingTaskResponse = storageService.getTask(token)
				.orElseThrow(() -> new Exception("Not active ML for selected token"));

		trainingTaskResponse.getTrainingTasks().forEach(task -> {
			int weightsDimension = task.getLernaMLParameters().getDimensions();
			for (Map.Entry<String, Long> job : task.getJobIds().entrySet()) {
				storageService.cleanupDeviceWeightsIncorrectDimension(job.getValue(), weightsDimension);
			}
		});
	}
}
