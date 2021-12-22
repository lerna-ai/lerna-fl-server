package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInference;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
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
import java.math.BigDecimal;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nd4j.linalg.factory.Nd4j;

@Component
public class FLManagerImpl implements FLManager {

	private final MpcService mpcService;
	private final LernaAppRepository lernaAppRepository;
	private final LernaMLRepository lernaMLRepository;
	private final LernaJobRepository lernaJobRepository;
	private final StorageService storageService;

	@Value("${app.config.mpcServer.host:localhost}")
	private String mpcHost;

	@Value("${app.config.mpcServer.port:31337}")
	private int mpcPort;
	
	private int scaling_factor = 100000;

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
	public String saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) throws Exception {
		if (!storageService.existsDeviceIdOnDropTable(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId())) {
			throw new Exception("Device ID not exists on pending devices list");
		}
		// ToDo: remove from job's drop table,
		if (lernaMLRepository.existsByAppToken(token)) {
			//!!!This jobId should not be the same as the id of the Jobs table, it is the id from the MPC server
			storageService.removeDeviceIdFromDropTable(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId());
			storageService.addDeviceWeights(trainingWeightsRequest.getJobId(), trainingWeightsRequest.getDeviceId(), trainingWeightsRequest.getDeviceWeights());
			checkNaggregate(trainingWeightsRequest.getJobId(), lernaMLRepository.findUsersNumByAppToken(token));
			return "OK";
			// ?ToDo: Check if enough weights have been gathered for aggregating the model/finishing the job
		} else {
			return null;
		}
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
	public String saveInference(String token, TrainingInferenceRequest trainingInferenceRequest) {

		if (lernaMLRepository.existsByAppToken(token)) {
			for (TrainingInference result : trainingInferenceRequest.getPrediction()) {
				storageService.addDeviceInference(result.getMl_id(), trainingInferenceRequest.getDeviceId(), trainingInferenceRequest.getVersion(), result.getModel(), result.getPrediction());
			}
			return "OK";
		} else {
			return null;
		}
	}

	@Override
	public String checkNaggregate(Long jobId, int num_of_users) { //this function aggragates per job, which is fine, but how do we follow versioning which is per app?
		int actual_users=storageService.getDeviceWeights(jobId).size(); //probably for just the size we do not want to return the whole list - maybe create a function just for the list size?
		if (actual_users >= num_of_users) { 
			List<INDArray> weights = storageService.getDeviceWeights(jobId);
			INDArray sum = Nd4j.zeros(weights.get(0).columns(), 1);
			for (INDArray w : weights) {
				sum = sum.add(w);
			}
			List<BigDecimal> shareList = mpcService.getLernaNoise(mpcHost, mpcPort, jobId, new ArrayList<>(storageService.getDeviceDropTable(jobId))).getShare();
			INDArray shares = Nd4j.zeros(weights.get(0).columns(), 1);
			for (int k = 0; k < weights.get(0).columns(); k++) {
				shares.putScalar(k, shareList.get(k).doubleValue());
			}
			//these are the final weights for this job
			sum = sum.add(shares).mul(1.0 / (actual_users * scaling_factor));
			
			//delete drop table, delete individual weights, job done
			//save new weights, update version?
			
			return ("Done");
		} else {
			return ("Not ready");
		}
	}
}
