package ai.lerna.flapi.service;

import ai.lerna.flapi.entity.LernaPrediction;

public interface WebhookService {
	void sendPrediction(LernaPrediction lernaPrediction);
	void cacheWebhookConfiguration();
}
