package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.Success;
import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingInferenceRequest;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;

public interface FLManager {

	TrainingTaskResponse getNewTraining(String token, Long deviceId) throws Exception;

	void saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) throws Exception;

	TrainingWeightsResponse getGlobalWeights(String token, long version);

	void saveDeviceAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest) throws Exception;

	void saveInference(String token, TrainingInferenceRequest trainingInferenceRequest) throws Exception;

	void saveSuccess(String token, Success success) throws Exception;

	void startup() throws Exception;

	void prepareTrainingTasks() throws Exception;

	void replaceAllJobs() throws Exception;

	void replaceJobs(String token);

	void cleanupDeviceWeights(String token) throws Exception;

	void checkAndAggregateAll() throws Exception;

	void checkAndAggregate(String token) throws Exception;
}
