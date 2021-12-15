package ai.lerna.flapi.manager;


import ai.lerna.flapi.api.dto.TrainingInitResponse;
import ai.lerna.flapi.service.MpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FLManagerImpl implements FLManager {
	private final MpcService mpcService;

	// ToDo: Apply per developer/ML configuration and move it to persist layer
	static final BigDecimal eps = BigDecimal.ZERO;
	static final int d = 5;
	static final int iter = 30;
	static final int epoch = 5;
	static final BigDecimal alpha = BigDecimal.ONE;
	static int users = 2;

	@Autowired
	public FLManagerImpl(MpcService mpcService) {
		this.mpcService = mpcService;
	}

	public TrainingInitResponse getNewTraining(String token) {
		// ToDo: Add implementation:

		return new TrainingInitResponse();
	}
}
