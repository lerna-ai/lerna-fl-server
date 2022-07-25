package ai.lerna.flapi.service;

import ai.lerna.flapi.entity.LernaPrediction;

public interface WebhookTemplateService {
	String getSlackTemplate(LernaPrediction prediction);
	String getBasicTemplate(LernaPrediction prediction);
	String getFirebaseTemplate(LernaPrediction prediction);
}
