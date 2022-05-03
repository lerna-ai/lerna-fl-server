package ai.lerna.flapi.service;

import ai.lerna.flapi.config.JacksonConfiguration;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.service.dto.WebhookQueueMessage;
import ai.lerna.flapi.service.dto.WebhookQueueMessageType;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Profile("!no-queue")
public class WebhookServiceQueueImpl implements WebhookService {

	@Value("${app.config.webhook.queueName}")
	String queueName;

	private final QueueMessagingTemplate queueMessagingTemplate;
	private final WebhookTemplateService webhookTemplateService;
	private final List<Long> DEVICE_IDS = Arrays.asList(403781509L, 1380808704L);
	private final List<String> PREDICTIONS = Arrays.asList("1", "5");
	protected final static ObjectMapper mapper = JacksonConfiguration.getNewObjectMapper();

	@Autowired
	public WebhookServiceQueueImpl(WebhookTemplateService webhookTemplateService) {
		this.webhookTemplateService = webhookTemplateService;
		AmazonSQSAsync amazonSQSAsync = AmazonSQSAsyncClientBuilder.defaultClient();
		this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
	}

	@Override
	public void sendPrediction(LernaPrediction lernaPrediction) {
		if (DEVICE_IDS.contains(lernaPrediction.getDeviceId()) && PREDICTIONS.contains(lernaPrediction.getPrediction())) {
			Optional.ofNullable(generateMessage(lernaPrediction)).ifPresent(message -> {
				this.queueMessagingTemplate.send(queueName, MessageBuilder.withPayload(message).build());
			});
		}
	}

	private String generateMessage(LernaPrediction lernaPrediction) {
		WebhookQueueMessage message = new WebhookQueueMessage();
		message.setType(WebhookQueueMessageType.Slack);
		message.setPayload(webhookTemplateService.getBasicTemplate(lernaPrediction));
		try {
			//message.setPayload(mapper.writeValueAsString(lernaPrediction));
			return mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
