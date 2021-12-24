package ai.lerna.flapi.api;

import ai.lerna.flapi.service.MpcService;
import ai.lerna.flapi.service.dto.MpcResponse;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
public class TestApiImpl implements TestApi {
	static final BigDecimal eps = BigDecimal.ZERO;
	static final int d = 5;
	static final int iter = 30;
	static final int epoch = 5;
	static final BigDecimal alpha = BigDecimal.ONE;
	static int users = 2;

	@Value("${app.config.mpcServer.host:localhost}")
	private String mpcHost;

	@Value("${app.config.mpcServer.port:31337}")
	private int mpcPort;

	private final MpcService mpcService;

	@Autowired
	public TestApiImpl(MpcService mpcService) {
		this.mpcService = mpcService;
	}

	public String index() {
		return "FL API";
	}

	public MpcResponse lerna() {
		return mpcService.getLernaJob(mpcHost, mpcPort, eps, d, alpha);
	}

	public MpcResponse lernaByJob(@PathVariable int jobId) {
		return mpcService.getLernaNoise(mpcHost, mpcPort, jobId, new ArrayList<>());
	}

	@Override
	public INDArray getINDArray(double d) {
		return Nd4j.zeros(5).add(d);
	}

}