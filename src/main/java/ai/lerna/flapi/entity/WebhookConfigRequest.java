package ai.lerna.flapi.entity;

public class WebhookConfigRequest {
	private String method;
	private String uri;
	private String fcmServerKey;
	private String notificationTitle;
	private String notificationBody;

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

	public String getFcmServerKey() {
		return fcmServerKey;
	}

	public void setFcmServerKey(String fcmServerKey) {
		this.fcmServerKey = fcmServerKey;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationBody() {
		return notificationBody;
	}

	public void setNotificationBody(String notificationBody) {
		this.notificationBody = notificationBody;
	}
}
