package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingTask;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeights;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.repository.LernaJobRepository;
import ai.lerna.flapi.repository.LernaMLRepository;
import ai.lerna.flapi.service.MpcService;
import ai.lerna.flapi.service.StorageService;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FLManagerImpl implements FLManager {

	private final MpcService mpcService;
	private final LernaAppRepository lernaAppRepository;
	private final LernaMLRepository lernaMLRepository;
	private final LernaJobRepository lernaJobRepository;
	private final StorageService storageService;

	// ToDo: Apply per developer/ML configuration and move it to persist layer
//	static final BigDecimal epsilon = BigDecimal.ZERO;
//	static final int dimensions = 5;
//	static final int iterations = 30;
//	static final BigDecimal normalization = BigDecimal.ONE;
//	static int minNoUsers = 2;

	@Value("${app.config.mpcServer.host:localhost}")
	private String mpcHost;

	@Value("${app.config.mpcServer.port:31337}")
	private int mpcPort;

	@Autowired
	public FLManagerImpl(MpcService mpcService, LernaAppRepository lernaAppRepository, LernaMLRepository lernaMLRepository, LernaJobRepository lernaJobRepository, StorageService storageService) {
		this.mpcService = mpcService;
		this.lernaAppRepository = lernaAppRepository;
		this.lernaMLRepository = lernaMLRepository;
		this.lernaJobRepository = lernaJobRepository;
		this.storageService = storageService;
	}

	@Override
	public TrainingTaskResponse getNewTraining(String token, Long deviceId) {
		TrainingTaskResponse trainingTaskResponse;
		Optional<TrainingTaskResponse> taskResponse = storageService.getTask(token);
		if (taskResponse.isPresent()) {
			trainingTaskResponse = taskResponse.get();
		} else {
			// ToDo: Add implementation:
			// Create drop tables per job and add device id
			trainingTaskResponse = createTrainingTask(token);
			storageService.putTask(token, trainingTaskResponse);
		}

		storageService.putDeviceIdToDropTables(trainingTaskResponse.getTrainingTasks(), deviceId);
		return trainingTaskResponse;
	}

	@Override
	public String saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) {
		// ToDo: remove from job's drop table,
		if(!lernaMLRepository.findAllByAppToken(token).isEmpty()){
			//!!!This jobId should not be the same as the id of the Jobs table, it is the id from the MPC server
			storageService.removeDeviceIdFromDropTables(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId());
			storageService.addDeviceWeights(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId(), trainingWeightsRequest.getDeviceWeights());
		// ToDo: save on redis and/or aggregate
			return "OK";
		// ?ToDo: Check if enough weights have been gathered for aggregating the model/finishing the job
		}
		else
			return null;
	}

	@Override
	public TrainingWeightsResponse getGlobalWeights(String token, long version) {
		// if user provide latest version return null
		TrainingWeightsResponse trainingWeightsResponse;
		Optional<TrainingWeightsResponse> weightResponse = storageService.getWeights(token);
		if (weightResponse.isPresent()) {
			long cur_version = weightResponse.get().getVersion();
			if (cur_version == version) {
				trainingWeightsResponse = null;
			} else {
				trainingWeightsResponse = weightResponse.get();
			}
		} else {

			trainingWeightsResponse = createTrainingWeights(token);

			storageService.putWeights(token, trainingWeightsResponse);
		}
		return trainingWeightsResponse;
	}

	@Override
	public String saveDeviceAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest) {
		// if not exist combination of ml_id, deviceId, version add new record
		// add to new tables :
		// ML_History FK(ml_id), version, weights [, sum(accuracy), avg(accuracy)]
		// ML_History_datapoints FK(ML_History.id, timestamp, deviceId, accuracy)
		return null;
	}

	private TrainingTaskResponse createTrainingTask(String token) {
		List<TrainingTask> trainingTasks = new ArrayList<>();
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

		//lernaAppRepository.incrementVersionByToken(token);
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
}
