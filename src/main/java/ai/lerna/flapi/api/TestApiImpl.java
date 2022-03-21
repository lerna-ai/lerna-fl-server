package ai.lerna.flapi.api;

import ai.lerna.flapi.entity.LernaJob;
import ai.lerna.flapi.repository.LernaJobRepository;
import ai.lerna.flapi.service.MpcService;
import ai.lerna.flapi.service.dto.MpcResponse;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.string.NDArrayStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

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
	private final LernaJobRepository lernaJobRepository;

	@Autowired
	public TestApiImpl(MpcService mpcService, LernaJobRepository lernaJobRepository) {
		this.mpcService = mpcService;
		this.lernaJobRepository = lernaJobRepository;
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
	public Map<String, String> jobsById(String token) {
		return lernaJobRepository.findAllByAppToken(token).stream()
				.collect(Collectors.toMap(LernaJob::getPrediction, j -> new NDArrayStrings(10).format(j.getWeights())));
	}

	@Override
	public INDArray getINDArray(double d) {
		return Nd4j.zeros(5, 1).add(d);
	}

	@Override
	public INDArray getINDArraySized(long rows) {
		return Nd4j.randn(rows, 1);
	}

	@Override
	public String inspectArray(INDArray array) throws Exception {
		 return array.toString();
	}

}