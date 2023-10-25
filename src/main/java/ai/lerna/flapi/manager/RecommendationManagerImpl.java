package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.RecommendationCategoryItem;
import ai.lerna.flapi.api.dto.RecommendationEngineRequest;
import ai.lerna.flapi.api.dto.RecommendationEvent;
import ai.lerna.flapi.api.dto.RecommendationItemScore;
import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.config.JacksonConfiguration;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.service.RecommendationService;
import ai.lerna.flapi.api.dto.RecommendationCategoryItem;
import ai.lerna.flapi.service.actionML.dto.Engine;
import ai.lerna.flapi.service.actionML.dto.EngineAlgorithm;
import ai.lerna.flapi.service.actionML.dto.EngineAlgorithmRanking;
import ai.lerna.flapi.service.actionML.dto.EngineConfig;
import ai.lerna.flapi.service.actionML.dto.EngineDataset;
import ai.lerna.flapi.service.actionML.dto.EngineSparkConf;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.service.actionML.dto.ItemResponse;
import ai.lerna.flapi.service.actionML.dto.ItemScore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RecommendationManagerImpl implements RecommendationManager {
	protected static final ObjectMapper mapper = JacksonConfiguration.getNewObjectMapper();
	private final RecommendationService recommendationService;
	private final LernaAppRepository lernaAppRepository;
	private final Map<String, String> hostMap = new HashMap<>();

	@Value("${app.config.actionml.spark.master:local}")
	private String sparkMaster;

	@Value("${app.config.actionml.spark.esNode:host.docker.internal}")
	private String sparkEsNode;

	@Value("${app.config.actionml.engineFactory:com.actionml.engines.ur.UREngine}")
	private String engineFactory;

	@Autowired
	public RecommendationManagerImpl(RecommendationService recommendationService, LernaAppRepository lernaAppRepository) {
		this.recommendationService = recommendationService;
		this.lernaAppRepository = lernaAppRepository;
	}

	@Override
	public void evictHostCache() {
		hostMap.clear();
	}

	@Override
	public void evictHostCache(String token) {
		hostMap.remove(token);
	}

	@Override
	public EventResponse createEngine(String token, String engineId) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		Engine engine = Engine.newBuilder()
				.setEngineId(engineId)
				.setEngineFactory("com.actionml.engines.ur.UREngine")
				.setComment("setup for fl-api")
				.setAlgorithm(EngineAlgorithm.newBuilder().setIndicatorsByList(Collections.singletonList("pv")).build())
				.setSparkConf(EngineSparkConf.newBuilderDefaultValues().build())
				.build();
		return recommendationService.createEngine(host, engine);
	}

	@Override
	public EventResponse createEngine(String token, RecommendationEngineRequest engineRequest) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.createEngine(host, convertEngine.apply(engineRequest));
	}

	@Override
	public EventResponse updateEngine(String token, String engineId) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		Engine engine = Engine.newBuilder()
				.setEngineId(engineId)
				.setEngineFactory("com.actionml.engines.ur.UREngine")
				.setComment("setup for fl-api, updated")
				.setAlgorithm(EngineAlgorithm.newBuilder().setIndicatorsByList(Arrays.asList("pv", "cart")).build())
				.setSparkConf(EngineSparkConf.newBuilderDefaultValues().build())
				.build();
		return recommendationService.updateEngine(host, engine);
	}

	@Override
	public EventResponse updateEngine(String token, RecommendationEngineRequest engineRequest) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.updateEngine(host, convertEngine.apply(engineRequest));
	}

	@Override
	public void deleteEngine(String token, String engineId) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return;
		}
		recommendationService.deleteEngine(host, engineId);
	}

	@Override
	public EventResponse trainEngine(String token, String engineId) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.trainEngine(host, engineId);
	}

	@Override
	public EventResponse cancelTrainEngine(String token, String engineId, String jobId) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.cancelTrainEngine(host, engineId, jobId);
	}

	@Override
	public List<EngineConfig> getEnginesStatus(String token) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return new ArrayList<>();
		}
		return Arrays.stream(recommendationService.statusEngines(host)).collect(Collectors.toList());
	}

	@Override
	public EngineConfig getEngineStatus(String token, String engineId) {
		String host = getHost(token, "");
		if (Strings.isNullOrEmpty(host)) {
			return null;
		}
		return recommendationService.statusEngine(host, engineId);
	}

	@Override
	public EventResponse sendEvent(String token, RecommendationEvent event) {
		String host = getHost(token, event.getEngineId());
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.sendEvent(host, new Event()
				.engineId(event.getEngineId())
				.event(event.getEvent())
				.entityType("user")
				.entityId(event.getEntityId())
				.targetEntityType("item")
				.targetEntityId(event.getTargetEntityId())
				.properties(new HashMap<>())
				.eventTime(event.getEventTime())
				.creationTime(DateTime.now()));
	}

	@Override
	public EventResponse sendItem(String token, RecommendationCategoryItem item) {
		String host = getHost(token, item.getEngineId());
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.sendEvent(host, new Event()
				.engineId(item.getEngineId())
				.entityId(item.getItemId())
				.properties(item.getProperties())
				.event("$set")
				.entityType("item")
				.eventTime(item.getEventTime())
				.creationTime(DateTime.now()));
	}

	@Override
	public RecommendationItems getItems(String token, Item item) {
		String host = getHost(token, item.getEngineId());
		if (Strings.isNullOrEmpty(host)) {
			return new RecommendationItems();
		}
		ItemResponse items = recommendationService.getItems(host, item);
		return RecommendationItems.newBuilder()
				.setResult(items.getResult().stream().map(convert).collect(Collectors.toList()))
				.build();
	}

	private String getHost(String token, String engine) {
		if (hostMap.containsKey(token)) {
			return hostMap.get(token) + engine;
		}
		Optional<LernaApp> app = lernaAppRepository.findByToken(token);
		if (app.isPresent()
				&& Objects.nonNull(app.get().getMetadata())
				&& !Strings.isNullOrEmpty(app.get().getMetadata().getActionMLUri())) {
			hostMap.put(token, app.get().getMetadata().getActionMLUri());
			return hostMap.get(token) + engine;
		}
		return null;
	}

	private final Function<ItemScore, RecommendationItemScore> convert = item -> {
		try {
			Map<String, List<String>> items = new HashMap<>();
			for (Map.Entry<String, Object> entry : ((HashMap<String, Object>) (mapper.readValue(item.getProps(), HashMap.class))).entrySet()) {
				if (entry.getValue() instanceof List) {
					items.put(entry.getKey(), (List<String>) entry.getValue());
				} else if (entry.getValue() instanceof String) {
					items.put(entry.getKey(), Arrays.asList(entry.getValue().toString()));
				} else if (entry.getValue() instanceof Double) {
					items.put(entry.getKey(), Arrays.asList(entry.getValue().toString()));
				} else { // This case also include previous two, that's added just for reference
					items.put(entry.getKey(), Arrays.asList(entry.getValue().toString()));
				}
			}
			return RecommendationItemScore.newBuilder()
					.setItem(item.getItem())
					.setScore(item.getScore())
					.setProps(items)
					.build();
		} catch (JsonProcessingException e) {
			return RecommendationItemScore.newBuilder()
					.setItem(item.getItem())
					.setScore(item.getScore())
					.build();
		}
	};

	private String getRankingName(String rankingType) {
		switch (rankingType) {
			case "popular":
				return "popRank";
			case "trending":
				return "trendRank";
			case "hot":
				return "hotRank";
			case "random":
				return "uniqueRank";
			case "userDefined":
				return "userRank";
			default:
				return "unknownRank";
		}
	}

	private final Function<RecommendationEngineRequest, Engine> convertEngine = engineRequest -> Engine.newBuilder()
			.setEngineId(engineRequest.getEngineId())
			.setEngineFactory(engineFactory)
			.setComment(engineRequest.getComment())
			.setAlgorithm(EngineAlgorithm.newBuilder()
					.setIndicatorsByList(engineRequest.getAlgorithmIndicators())
					.setRankings(Collections.singletonList(EngineAlgorithmRanking.newBuilder()
							.setName(getRankingName(engineRequest.getAlgorithmRankingType()))
							.setType(engineRequest.getAlgorithmRankingType())
							.setIndicatorNames(engineRequest.getAlgorithmRankingIndicatorNames())
							.setDuration(engineRequest.getAlgorithmRankingDuration())
							.build()))
					.build())
			.setSparkConf(EngineSparkConf.newBuilderDefaultValues()
					.setMaster(sparkMaster)
					.setEsNodes(sparkEsNode)
					.build())
			.setDataset(EngineDataset.newBuilder()
					.setTtl(engineRequest.getDatasetTtl())
					.build())
			.build();
}
