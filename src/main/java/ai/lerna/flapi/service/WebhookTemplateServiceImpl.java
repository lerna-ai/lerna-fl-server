package ai.lerna.flapi.service;

import ai.lerna.flapi.entity.LernaPrediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class WebhookTemplateServiceImpl implements WebhookTemplateService {

	@Autowired
	@Qualifier("messageTemplateEngine")
	protected SpringTemplateEngine mMessageTemplateEngine;

	public String getSlackTemplate(LernaPrediction prediction) {
		final Context theContext = new Context();
		theContext.setVariable("Prediction", prediction);
		final String theJsonMessage =
				mMessageTemplateEngine.process("json/webhookSlack", theContext);
		return theJsonMessage;
	}

	public String getBasicTemplate(LernaPrediction prediction) {
		final Context theContext = new Context();
		theContext.setVariable("Prediction", prediction);
		final String theJsonMessage =
				mMessageTemplateEngine.process("json/lernaPrediction", theContext);
		return theJsonMessage;
	}

	@Override
	public String getFirebaseTemplate(LernaPrediction prediction) {
		final Context theContext = new Context();
		theContext.setVariable("Prediction", prediction);
		final String theJsonMessage =
				mMessageTemplateEngine.process("json/webhookFirebase", theContext);
		return theJsonMessage;
	}
}
