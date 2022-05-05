package ai.lerna.flapi.service.dto;

public class WebhookQueueMessage {
	private WebhookMessageType type;
	private String method;
	private String uri;
	private String payload;

	public WebhookMessageType getType() {
		return type;
	}

	public void setType(WebhookMessageType type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}
