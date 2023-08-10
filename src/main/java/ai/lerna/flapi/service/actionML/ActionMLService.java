package ai.lerna.flapi.service.actionML;

import ai.lerna.flapi.service.RecommendationService;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.service.actionML.dto.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActionMLService implements RecommendationService {
	private final RestTemplate restTemplate;

	@Autowired
	public ActionMLService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public EventResponse sendEvent(String host, Event event) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(event.toString(), headers);
		return restTemplate.postForObject(host + "/events", request, EventResponse.class);
	}

	@Override
	public ItemResponse getItems(String host, Item item) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(item.toString(), headers);
		return restTemplate.postForObject(host + "/queries", request, ItemResponse.class);
	}
}
