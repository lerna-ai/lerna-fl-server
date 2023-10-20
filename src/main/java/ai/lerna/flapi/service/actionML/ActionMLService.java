package ai.lerna.flapi.service.actionML;

import ai.lerna.flapi.service.RecommendationService;
import ai.lerna.flapi.service.actionML.dto.Engine;
import ai.lerna.flapi.service.actionML.dto.EngineConfig;
import ai.lerna.flapi.service.actionML.dto.Event;
import ai.lerna.flapi.service.actionML.dto.EventResponse;
import ai.lerna.flapi.service.actionML.dto.Item;
import ai.lerna.flapi.service.actionML.dto.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	public EventResponse createEngine(String host, Engine engine) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(engine.toString(), headers);
		return restTemplate.postForObject(host, request, EventResponse.class);
	}

	@Override
	public EventResponse updateEngine(String host, Engine engine) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(engine.toString(), headers);
		return restTemplate.postForObject(host + engine.getEngineId() + "/configs", request, EventResponse.class);
	}

	@Override
	public void deleteEngine(String host, String engineId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.delete(host + engineId);
	}

	@Override
	public EventResponse trainEngine(String host, String engineId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("", headers);
		return restTemplate.postForObject(host + engineId + "/jobs", request, EventResponse.class);

		//respose:
//		{
//			"description": {
//			"jobId": "e4bd16d1-de05-4824-8aaf-e353d4d6cc62",
//					"status": {
//				"name": "queued"
//			},
//			"comment": "Spark job",
//					"createdAt": "2023-10-20T12:12:52.877Z"
//		},
//			"comment": "Started train Job on Spark"
//		}
	}

	@Override
	public EventResponse cancelTrainEngine(String host, String engineId, String jobId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>("", headers);
		restTemplate.delete(host + engineId + "/jobs/" + jobId);
		return restTemplate.exchange(host + engineId + "/jobs/" + jobId, HttpMethod.DELETE, request, EventResponse.class).getBody();
	}

	@Override
	public EngineConfig[] statusEngines(String host) {
		return restTemplate.getForObject(host, EngineConfig[].class);
	}

	@Override
	public EngineConfig statusEngine(String host, String engineId) {
		return restTemplate.getForObject(host + engineId, EngineConfig.class);
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
