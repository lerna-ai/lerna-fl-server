package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.DeviceWeights;
import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInference;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeights;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.entity.MLHistory;
import ai.lerna.flapi.entity.MLHistoryDatapoint;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.repository.LernaJobRepository;
import ai.lerna.flapi.repository.LernaMLRepository;
import ai.lerna.flapi.repository.LernaPredictionRepository;
import ai.lerna.flapi.repository.MLHistoryDatapointRepository;
import ai.lerna.flapi.repository.MLHistoryRepository;
import ai.lerna.flapi.service.MpcService;
import ai.lerna.flapi.service.StorageService;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class FLManagerImpl implements FLManager {

	private final MpcService mpcService;
	private final LernaAppRepository lernaAppRepository;
	private final LernaMLRepository lernaMLRepository;
	private final LernaJobRepository lernaJobRepository;
	private final LernaPredictionRepository lernaPredictionRepository;
	private final MLHistoryRepository mlHistoryRepository;
	private final MLHistoryDatapointRepository mlHistoryDatapointRepository;
	private final StorageService storageService;

	@Value("${app.config.mpcServer.host:localhost}")
	private String mpcHost;

	@Value("${app.config.mpcServer.port:31337}")
	private int mpcPort;

	@Value("${app.config.fl.scalingFactor:100000}")
	private int scaling_factor;

	@Autowired
	public FLManagerImpl(MpcService mpcService, LernaAppRepository lernaAppRepository, LernaMLRepository lernaMLRepository, LernaJobRepository lernaJobRepository, LernaPredictionRepository lernaPredictionRepository, MLHistoryRepository mlHistoryRepository, MLHistoryDatapointRepository mlHistoryDatapointRepository, StorageService storageService) {
		this.mpcService = mpcService;
		this.lernaAppRepository = lernaAppRepository;
		this.lernaMLRepository = lernaMLRepository;
		this.lernaJobRepository = lernaJobRepository;
		this.lernaPredictionRepository = lernaPredictionRepository;
		this.mlHistoryRepository = mlHistoryRepository;
		this.mlHistoryDatapointRepository = mlHistoryDatapointRepository;
		this.storageService = storageService;
	}

	@Override
	public TrainingTaskResponse getNewTraining(String token, Long deviceId) throws Exception {
		TrainingTaskResponse trainingTaskResponse;
		Optional<TrainingTaskResponse> taskResponse = storageService.getTask(token);
		if (taskResponse.isPresent()) {
			trainingTaskResponse = taskResponse.get();
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting {0} TrainingTasks from cache", trainingTaskResponse.getTrainingTasks().size());
		} else {
			trainingTaskResponse = createTrainingTask(token);
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting {0} TrainingTasks from db", trainingTaskResponse.getTrainingTasks().size());
			storageService.putTask(token, trainingTaskResponse);
		}

		storageService.putDeviceIdToDropTable(trainingTaskResponse.getTrainingTasks(), deviceId);
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
		checkAndAggregate(token);
		// ?ToDo: Check if enough weights have been gathered for aggregating the model/finishing the job
	}

	@Override
	public TrainingWeightsResponse getGlobalWeights(String token, long version) {
		// if user provide latest version return null
		TrainingWeightsResponse trainingWeightsResponse;
		Optional<TrainingWeightsResponse> weightResponse = storageService.getWeights(token);
		if (weightResponse.isPresent()) {
			long cur_version = weightResponse.get().getVersion();
			trainingWeightsResponse = (cur_version != version)
					? weightResponse.get()
					: null;
		} else {

			trainingWeightsResponse = createTrainingWeights(token);

			storageService.putWeights(token, trainingWeightsResponse);
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
				.orElseGet(() -> mlHistoryRepository.save(constructMLHistory(trainingAccuracyRequest)));
		MLHistoryDatapoint mlHistoryDatapoint = constructMLHistoryDatapoint(mlHistory.getId(), trainingAccuracyRequest.getDeviceId(), trainingAccuracyRequest.getAccuracy());
		mlHistoryDatapointRepository.save(mlHistoryDatapoint);
		mlHistoryRepository.updateAccuracyAverage(mlHistory.getId());
	}

	private MLHistory constructMLHistory(TrainingAccuracyRequest trainingAccuracyRequest) {
		MLHistory mlHistory = new MLHistory();
		mlHistory.setMLId(trainingAccuracyRequest.getMlId());
		mlHistory.setVersion(trainingAccuracyRequest.getVersion());
		mlHistory.setWeights(Nd4j.zeros(5, 1));
		mlHistory.setAccuracyAvg(BigDecimal.ZERO);
		return mlHistory;
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
			lernaJobRepository.findByMLId(lernaML.getId()).forEach(lernaJob -> {
				jobIds.put(lernaJob.getPrediction(), mpcService.getLernaJob(mpcHost, mpcPort, lernaML.getPrivacy().getEpsilon(), lernaML.getML().getDimensions(), lernaML.getML().getNormalization()).getCompId());
			});
			trainingTasks.add(TrainingTask.newBuilder()
					.setJobIds(jobIds)
					.setMlId(lernaML.getId())
					.setMlModel(lernaML.getModel())
					.setLernaMLParameters(lernaML.getML())
					.build());
		});

		return TrainingTaskResponse.newBuilder()
				.setTrainingTasks(trainingTasks)
				.setVersion(lernaAppRepository.getVersionByToken(token).orElse(0L))
				.build();
	}

	private TrainingWeightsResponse createTrainingWeights(String token) {
		List<TrainingWeights> trainingWeights = new ArrayList<>();
		lernaMLRepository.findAllByAppToken(token).forEach(lernaML -> {
			Map<String, INDArray> weights = new HashMap<>();
			lernaJobRepository.findByMLId(lernaML.getId()).forEach(lernaJob -> {
				weights.put(lernaJob.getPrediction(), lernaJob.getWeights());
			});
			trainingWeights.add(TrainingWeights.newBuilder()
					.setMlId(lernaML.getId())
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
		for (TrainingInference result : trainingInferenceRequest.getTrainingInference()) {
			LernaPrediction lernaPrediction = LernaPrediction.newBuilder()
					.setDeviceId(trainingInferenceRequest.getDeviceId())
					.setMLId(result.getMl_id())
					.setModel(result.getModel())
					.setVersion(trainingInferenceRequest.getVersion())
					.setPrediction(result.getPrediction())
					.setTimestamp(new Date())
					.build();
			lernaPredictionRepository.save(lernaPrediction);
			storageService.addDeviceInference(token, lernaPrediction);
		}
	}

	private void checkAndAggregate(String token) throws Exception {
		int num_of_users = lernaMLRepository.findUsersNumByAppToken(token);
		TrainingTaskResponse trainingTaskResponse = storageService.getTask(token)
				.orElseThrow(() -> new Exception("Not active ML for selected token"));

		// ToDo: Check if we need to aggregate per task or per token
		if (!trainingTaskResponse.getTrainingTasks().stream()
				.flatMap(trainingTask -> trainingTask.getJobIds().values().stream())
				.allMatch(job -> storageService.getDeviceWeightsSize(job) >= num_of_users)) {
			return;
		}
		trainingTaskResponse.getTrainingTasks().forEach(task -> {
			long mlId = task.getMlId();
			for (Map.Entry<String, Long> job : task.getJobIds().entrySet()) {
				storageService.deactivateJob(job.getValue());
				List<DeviceWeights> weights = storageService.getDeviceWeights(job.getValue());
				if (Objects.isNull(weights)) {
					// Kill jobs from privacy server
					mpcService.getLernaNoise(mpcHost, mpcPort, job.getValue(), new ArrayList<>(storageService.getDeviceDropTable(job.getValue())));
					continue;
				}
				INDArray sum = Nd4j.zeros(weights.get(0).getWeights().rows(), 1);
				long total_points = 0;
				for (DeviceWeights w : weights) {
					total_points += w.getDataPoints();
					sum = sum.add(w.getWeights());
				}
				List<BigDecimal> shareList = mpcService.getLernaNoise(mpcHost, mpcPort, job.getValue(), new ArrayList<>(storageService.getDeviceDropTable(job.getValue()))).getShare();
				INDArray shares = Nd4j.zeros(weights.get(0).getWeights().rows(), 1);
				for (int k = 0; k < weights.get(0).getWeights().rows(); k++) {
					shares.putScalar(k, shareList.get(k).doubleValue());
				}
				//these are the final weights for this job
				sum = sum.add(shares).mul(1.0 / (storageService.getDeviceWeightsSize(job.getValue()) * scaling_factor));

				//update weights functionality
				INDArray finalSum = sum;
				long finalTotal_points = total_points;
				lernaJobRepository.findByMLIdAndPrediction(mlId, job.getKey()).stream()
						.peek(lernaJob -> {
							lernaJob.setWeights(finalSum);
							lernaJob.setTotalDataPoints(finalTotal_points);
							lernaJob.setTotalDevices(storageService.getDeviceWeightsSize(job.getValue()));
						})
						.map(lernaJobRepository::save)
						.collect(Collectors.toList());

				storageService.deleteDropTable(job.getValue());

			}
		});

		lernaAppRepository.incrementVersionByToken(token);
		storageService.deleteTaskTable(token);
		storageService.deleteWeightsTable(token);
	}
}
