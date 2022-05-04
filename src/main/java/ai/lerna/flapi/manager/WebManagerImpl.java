package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.LernaApplication;
import ai.lerna.flapi.api.dto.WebChartData;
import ai.lerna.flapi.api.dto.WebDashboard;
import ai.lerna.flapi.api.dto.Webhook;
import ai.lerna.flapi.api.dto.WebhookResponse;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.entity.LernaJob;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.entity.WebhookConfig;
import ai.lerna.flapi.entity.WebhookConfigFilter;
import ai.lerna.flapi.entity.WebhookConfigRequest;
import ai.lerna.flapi.repository.InferencesRepository;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.repository.LernaJobRepository;
import ai.lerna.flapi.repository.LernaMLRepository;
import ai.lerna.flapi.repository.LernaPredictionRepository;
import ai.lerna.flapi.repository.MLHistoryDatapointRepository;
import ai.lerna.flapi.repository.MLHistoryRepository;
import ai.lerna.flapi.repository.WebhookConfigRepository;
import ai.lerna.flapi.service.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class WebManagerImpl implements WebManager {
	private final LernaAppRepository lernaAppRepository;
	private LernaMLRepository lernaMLRepository;
	private final LernaJobRepository lernaJobRepository;
	private final LernaPredictionRepository lernaPredictionRepository;
	private final MLHistoryRepository mlHistoryRepository;
	private final InferencesRepository inferencesRepository;
	private final MLHistoryDatapointRepository mlHistoryDatapointRepository;
	private final WebhookConfigRepository webhookConfigRepository;
	private final WebhookService webhookService;

	public WebManagerImpl(LernaAppRepository lernaAppRepository, LernaMLRepository lernaMLRepository, LernaJobRepository lernaJobRepository, LernaPredictionRepository lernaPredictionRepository, MLHistoryRepository mlHistoryRepository, MLHistoryDatapointRepository mlHistoryDatapointRepository, InferencesRepository inferencesRepository, WebhookConfigRepository webhookConfigRepository, WebhookService webhookService) {
		this.lernaAppRepository = lernaAppRepository;
		this.lernaMLRepository = lernaMLRepository;
		this.lernaJobRepository = lernaJobRepository;
		this.lernaPredictionRepository = lernaPredictionRepository;
		this.mlHistoryRepository = mlHistoryRepository;
		this.mlHistoryDatapointRepository = mlHistoryDatapointRepository;
		this.inferencesRepository = inferencesRepository;
		this.webhookConfigRepository = webhookConfigRepository;
		this.webhookService = webhookService;
	}

	@Override
	public List<LernaPrediction> getInference(String token) throws Exception {
		if (!lernaMLRepository.existsByAppToken(token)) {
			throw new Exception("Not exists ML for selected token");
		}
		Map<String, String> predictionMap = lernaJobRepository.findJobPredictionMapByAppToken(token).stream()
				.collect(Collectors.toMap(p -> String.valueOf(p.getPredictionValue()), LernaJob::getPrediction));
		return lernaPredictionRepository.findLatestOneDayByToken(token).stream()
				.map(p -> replacePredictionWithLabel(p, predictionMap))
				.collect(Collectors.toList());
	}

	@Override
	public List<LernaApplication> getApplications(long userId, boolean includeML) {
		List<LernaApplication> lernaApplications = lernaAppRepository.findByUserIdOrderById(userId).stream().map(convert).collect(Collectors.toList());
		if (includeML) {
			lernaApplications = lernaApplications.stream().map(addML).collect(Collectors.toList());
		}
		return lernaApplications;
	}

	@Override
	public String getJSONFile(long userId, long appId, long mlId){
		Map<String, float[]> weights = new HashMap();
		lernaJobRepository.getModel(userId, appId, mlId).forEach(lernaJob -> {
			weights.put(lernaJob.getPrediction(), lernaJob.getWeights().toFloatVector());
		});
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			return mapper.writeValueAsString(weights);
		} catch (JsonProcessingException ex) {
			Logger.getLogger(WebManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
			return "";
		}
	}

	private final Function<WebhookConfig, Webhook> webhookConvert = webhookConfig -> Webhook.newBuilder()
			.setId(webhookConfig.getId())
			.setAppId(webhookConfig.getAppId())
			.setType(webhookConfig.getType())
			.setMethod(webhookConfig.getRequest().getMethod())
			.setUri(webhookConfig.getRequest().getUri())
			.setCategories(webhookConfig.getFilter().getCategories())
			.setEnabled(webhookConfig.isEnabled())
			.build();

	private final Function<Webhook, WebhookConfig> webhookConfigConvert = webhook -> {
		WebhookConfigRequest request = new WebhookConfigRequest();
		request.setMethod(webhook.getMethod());
		request.setUri(webhook.getUri());
		WebhookConfigFilter filter = new WebhookConfigFilter();
		filter.setCategories(webhook.getCategories());
		WebhookConfig webhookConfig = new WebhookConfig();
		webhookConfig.setId(webhook.getId());
		webhookConfig.setAppId(webhook.getAppId());
		webhookConfig.setType(webhook.getType());
		webhookConfig.setRequest(request);
		webhookConfig.setFilter(filter);
		webhookConfig.setEnabled(webhook.isEnabled());
		return webhookConfig;
	};

	@Override
	public WebhookResponse getWebhookConfig(long userId, long appId) {
		return WebhookResponse.newBuilder()
				.setWebhooks(webhookConfigRepository.getWebhookConfig(userId, appId).stream()
						.map(webhookConvert)
						.collect(Collectors.toList()))
				.build();
	}

	@Override
	public Map<String, Long> getWebhookCategories(long userId, long appId) {
		return lernaJobRepository.getCategories(userId, appId).stream().collect(Collectors.toMap(LernaJob::getPrediction, LernaJob::getPredictionValue));
	}

	@Override
	public String saveWebhookConfig(long userId, Webhook webhook) {
		webhookConfigRepository.save(webhookConfigConvert.apply(webhook));
		webhookService.cacheWebhookConfiguration();
		return "OK";
	}

	@Override
	public List<Map<String, BigInteger>> getActiveDevices(long userId, long appId) {
		return lernaPredictionRepository.findDevicePredictionPerWeek(userId, appId);
	}

	@Override
	public WebDashboard getDashboardData(long userId, long appId) {
		List<BigDecimal> accuracies = mlHistoryRepository.getAccuracies(userId, appId);
		BigDecimal accuracyPrevious = inferencesRepository.getPreviousSuccesses();
		BigDecimal accuracyLatest = inferencesRepository.getLatestSuccesses();
		long totalData = lernaJobRepository.getTotalDataPoints(userId, appId);
		long devicesParticipating = mlHistoryDatapointRepository.getTotalDevicesLastWeek(userId, appId); //lernaJobRepository.getTotalDevices(userId, appId);
		long devicesTotalLastWeek = lernaPredictionRepository.getTotalDevicesLastWeek(userId, appId);
		long devicesTotalPreviousWeek = lernaPredictionRepository.getTotalDevicesPreviousWeek(userId, appId);
		long learningIterations = lernaAppRepository.getByUserId(userId, appId).orElseGet(LernaApp::new).getVersion()-1;
		//accuracies.remove(accuracies.size()-1);
		return WebDashboard.newBuilder()
				.setSuccessPrediction(accuracyLatest.longValue())
				.setSuccessPredictionTrend(accuracyLatest.subtract(accuracyPrevious).longValue())
				.setTotalData(totalData * 1024)
				.setTotalDevices(devicesTotalLastWeek)
				.setTotalDevicesTrend(getRate(BigDecimal.valueOf(devicesTotalLastWeek), BigDecimal.valueOf(devicesTotalPreviousWeek)))
				.setLearningIterations(learningIterations)
				.setSuccessPredictionRate(WebChartData.newBuilder()
						.setLabels(IntStream.rangeClosed(1, accuracies.size()).mapToObj(Integer::toString).collect(Collectors.toList()))
						.setData(accuracies.stream().map(a -> Optional.ofNullable(a).orElse(BigDecimal.ZERO)).map(BigDecimal::doubleValue).collect(Collectors.toList()))
						.build())
				.setDeviceParticipating(devicesParticipating)
				.build();
	}

	private final Function<LernaApp, LernaApplication> convert = lernaApp -> LernaApplication.newBuilder()
			.setId(lernaApp.getId())
			.setToken(lernaApp.getToken())
			.setVersion(lernaApp.getVersion())
			.setMinNoUsers(lernaApp.getMinNoUsers())
			.build();

	private LernaPrediction replacePredictionWithLabel(LernaPrediction prediction, Map<String, String> predictionMap) {
		return LernaPrediction.newBuilder(prediction)
				.setPrediction(predictionMap.get(prediction.getPrediction()))
				.build();
	}

	private final UnaryOperator<LernaApplication> addML = lernaApplication -> LernaApplication.newBuilder(lernaApplication)
			.setMls(lernaMLRepository.findByAppIdOrderById(lernaApplication.getId()))
			.build();

	private long getRate(BigDecimal current, BigDecimal previous) {
		if (previous.equals(BigDecimal.ZERO)) {
			return 0;
		}
		return current.subtract(previous).divide(previous, 10, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100))
				.longValue();
	}
}
