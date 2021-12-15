package ai.lerna.flapi.manager;


import ai.lerna.flapi.api.dto.TrainingInitResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.service.MpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FLManagerImpl implements FLManager {
	private final MpcService mpcService;

	// ToDo: Apply per developer/ML configuration and move it to persist layer
	static final BigDecimal epsilon = BigDecimal.ZERO;
	static final int dimensions = 5;
	static final int iterations = 30;
	static final BigDecimal normalization = BigDecimal.ONE;
	static int minNoUsers = 2;

	@Autowired
	public FLManagerImpl(MpcService mpcService) {
		this.mpcService = mpcService;
	}

	public TrainingInitResponse getNewTraining(String token) {
		// ToDo: Add implementation:

		return new TrainingInitResponse();
	}
        
        public TrainingWeightsResponse getAccuracy(String token) {
		// ToDo: Add implementation:

		return new TrainingWeightsResponse();
	}
}
