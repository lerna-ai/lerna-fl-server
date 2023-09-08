package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.RecommendationItemScore;
import ai.lerna.flapi.api.dto.RecommendationItems;
import ai.lerna.flapi.config.JacksonConfiguration;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.repository.LernaAppRepository;
import ai.lerna.flapi.service.RecommendationService;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.service.actionML.dto.ItemResponse;
import ai.lerna.flapi.service.actionML.dto.ItemScore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
	public EventResponse sendEvent(String token, Event event) {
		String host = getHost(token, event.getEngineId());
		if (Strings.isNullOrEmpty(host)) {
			return new EventResponse();
		}
		return recommendationService.sendEvent(host, event);
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
}
