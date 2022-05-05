package ai.lerna.flapi.entity;

public class WebhookConfigRequest {
	private String method;
	private String uri;

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
}
