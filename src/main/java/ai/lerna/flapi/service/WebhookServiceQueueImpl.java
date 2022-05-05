package ai.lerna.flapi.service;

import ai.lerna.flapi.config.JacksonConfiguration;
import ai.lerna.flapi.entity.LernaJob;
import ai.lerna.flapi.entity.LernaML;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.entity.WebhookConfig;
import ai.lerna.flapi.repository.LernaJobRepository;
import ai.lerna.flapi.repository.LernaMLRepository;
import ai.lerna.flapi.repository.WebhookConfigRepository;
import ai.lerna.flapi.service.dto.WebhookQueueMessage;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("!no-queue")
public class WebhookServiceQueueImpl implements WebhookService {

	@Value("${app.config.webhook.queueName}")
	String queueName;

	private final LernaMLRepository lernaMLRepository;
	private final LernaJobRepository lernaJobRepository;
	private final WebhookConfigRepository webhookConfigRepository;
	private final QueueMessagingTemplate queueMessagingTemplate;
	private final WebhookTemplateService webhookTemplateService;
	protected final static ObjectMapper mapper = JacksonConfiguration.getNewObjectMapper();
	private Map<Long, Long> mlIdAppIdMap;
	private Map<Long, WebhookConfig> webhookConfigs;
	private Map<Long, Map<String, String>> predictionMap;

	@Autowired
	public WebhookServiceQueueImpl(WebhookTemplateService webhookTemplateService, LernaMLRepository lernaMLRepository, LernaJobRepository lernaJobRepository, WebhookConfigRepository webhookConfigRepository) {
		this.webhookTemplateService = webhookTemplateService;
		this.lernaMLRepository = lernaMLRepository;
		this.lernaJobRepository = lernaJobRepository;
		this.webhookConfigRepository = webhookConfigRepository;
		AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.defaultClient();
		this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
		cacheWebhookConfiguration();
	}

	@Override
	public void sendPrediction(LernaPrediction lernaPrediction) {
		if (!mlIdAppIdMap.containsKey(lernaPrediction.getMLId())
				|| !webhookConfigs.containsKey(mlIdAppIdMap.get(lernaPrediction.getMLId()))) {
			// webhook not configured for selected ML ID
			return;
		}
		WebhookConfig webhookConfig = webhookConfigs.get(mlIdAppIdMap.get(lernaPrediction.getMLId()));

		if (webhookConfig.getFilter().getCategories().contains(lernaPrediction.getPrediction())) {
			Optional.ofNullable(generateMessage(lernaPrediction, webhookConfig)).ifPresent(message -> {
				this.queueMessagingTemplate.send(queueName, MessageBuilder.withPayload(message).build());
			});
		}
	}

	@Override
	public void cacheWebhookConfiguration() {
		mlIdAppIdMap = lernaMLRepository.findAll().stream()
				.collect(Collectors.toMap(LernaML::getId, LernaML::getAppId));
		webhookConfigs = webhookConfigRepository.findAllByEnabledTrue().stream()
				.collect(Collectors.toMap(WebhookConfig::getAppId, webhookConfig -> webhookConfig));
		predictionMap = new HashMap<>();
		webhookConfigs.keySet().forEach(appId -> {
			predictionMap.put(appId, lernaJobRepository.getCategories(appId).stream().collect(Collectors.toMap(lernaJob -> String.valueOf(lernaJob.getPredictionValue()), LernaJob::getPrediction)));
		});
	}

	private String generateMessage(LernaPrediction lernaPrediction, WebhookConfig webhookConfig) {
		lernaPrediction = LernaPrediction.newBuilder(lernaPrediction)
				.setPrediction(predictionMap.get(mlIdAppIdMap.get(lernaPrediction.getMLId()))
						.getOrDefault(lernaPrediction.getPrediction(), lernaPrediction.getPrediction()))
				.build();
		WebhookQueueMessage message = new WebhookQueueMessage();
		message.setType(webhookConfig.getType());
		message.setMethod(webhookConfig.getRequest().getMethod());
		message.setUri(webhookConfig.getRequest().getUri());
		switch (webhookConfig.getType()) {
			case Slack:
				message.setPayload(webhookTemplateService.getSlackTemplate(lernaPrediction));
				break;
			case Webhook:
			default:
				message.setPayload(webhookTemplateService.getBasicTemplate(lernaPrediction));
				break;
		}
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
