package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.entity.LernaML;
import ai.lerna.flapi.entity.LernaMLParameters;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.repository.LernaMLRepository;
import ai.lerna.flapi.service.StorageService;
import ai.lerna.flapi.service.MpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FLManagerImpl implements FLManager {
	private final MpcService mpcService;
	private final LernaAppRepository lernaAppRepository;
	private final LernaMLRepository lernaMLRepository;
	private final StorageService storageService;

	// ToDo: Apply per developer/ML configuration and move it to persist layer
	static final BigDecimal epsilon = BigDecimal.ZERO;
	static final int dimensions = 5;
	static final int iterations = 30;
	static final BigDecimal normalization = BigDecimal.ONE;
	static int minNoUsers = 2;

	@Autowired
	public FLManagerImpl(MpcService mpcService, LernaAppRepository lernaAppRepository, LernaMLRepository lernaMLRepository, StorageService storageService) {
		this.mpcService = mpcService;
		this.lernaAppRepository = lernaAppRepository;
		this.lernaMLRepository = lernaMLRepository;
		this.storageService = storageService;
	}

	public TrainingTaskResponse getNewTraining(String token) {
		// ToDo: Add implementation:
		TrainingTaskResponse trainingTaskResponse = new TrainingTaskResponse();
//		lernaStorageService.getTask(token).ifPresent(task -> {
//			return task;
//		});

		lernaAppRepository.findByToken(token).ifPresent(lernaApp -> {
			List<LernaMLParameters> lernaMLs = lernaMLRepository.findByAppId(lernaApp.getId()).stream().map(LernaML::getML).collect(Collectors.toList());
			storageService.putTask(token, trainingTaskResponse);
		});

		return new TrainingTaskResponse();
	}

	@Override
	public String saveDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) {
		return null;
	}

	@Override
	public TrainingWeightsResponse getGlobalWights(String token, long jobId) {
		return null;
	}
}
