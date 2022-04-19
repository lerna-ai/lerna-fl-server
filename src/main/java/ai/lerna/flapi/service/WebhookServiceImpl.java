package ai.lerna.flapi.service;

import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.repository.LernaJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("no-queue")
public class WebhookServiceImpl implements WebhookService {
	private final LernaJobRepository LernaJobRepository;
	private final RestTemplate restTemplate;
	private final List<Long> DEVICE_IDS = Arrays.asList(403781509L, 1380808704L);
	private final List<String> PREDICTIONS = Arrays.asList("1", "5");
	private final Map<String, String> predictionMap;

	private final String SLACK_ENDPOINT = "https://hooks.slack.com/services/insert/your/token";

	@Autowired
	public WebhookServiceImpl(LernaJobRepository lernaJobRepository, RestTemplate restTemplate) {
		LernaJobRepository = lernaJobRepository;
		this.restTemplate = restTemplate;
		predictionMap = new HashMap<>();
		predictionMap.put("1","audio");
		predictionMap.put("2","game");
		predictionMap.put("3","image");
		predictionMap.put("4","maps");
		predictionMap.put("5","news");
		predictionMap.put("6","productivity");
		predictionMap.put("7","social");
		predictionMap.put("8","video");
	}

	@Override
	public void sendPrediction(LernaPrediction lernaPrediction) {
		if (DEVICE_IDS.contains(lernaPrediction.getDeviceId()) && PREDICTIONS.contains(lernaPrediction.getPrediction())) {
			restTemplate.postForObject(SLACK_ENDPOINT, generateMessage(lernaPrediction), String.class);
		}
	}

	private String generateMessage(LernaPrediction lernaPrediction) {
		return "{\"blocks\":[" +
				"{\"type\":\"header\",\"text\":{\"type\":\"plain_text\",\"text\":\"New prediction\",\"emoji\":true}}," +
				"{\"type\":\"section\",\"fields\":[" +
				"{\"type\":\"mrkdwn\",\"text\":\"*Device ID:*\\n" + lernaPrediction.getDeviceId() + "\"}," +
				"{\"type\":\"mrkdwn\",\"text\":\"*When:*\\n" + lernaPrediction.getTimestamp() + "\"}" +
				"]}," +
				"{\"type\":\"section\",\"fields\":[" +
				"{\"type\":\"mrkdwn\",\"text\":\"*Model:*\\n"+ lernaPrediction.getModel() + "\"}," +
				"{\"type\":\"mrkdwn\",\"text\":\"*Version:*\\n"+ lernaPrediction.getVersion() + "\"}" +
				"]}," +
				"{\"type\":\"section\",\"fields\":[" +
				"{\"type\":\"mrkdwn\",\"text\":\"*Prediction:*\\n"+ predictionMap.getOrDefault(lernaPrediction.getPrediction(), lernaPrediction.getPrediction()) + "\"}" +
				"]}]}";
	}
}
