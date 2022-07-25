package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.service.dto.WebhookMessageType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Webhook configuration")
public class Webhook {

	@Schema(description = "Webhook ID")
	private Long id;

	@Schema(description = "Application ID")
	private long appId;

	@Schema(description = "Webhook type")
	private WebhookMessageType type;

	@Schema(description = "Request method")
	private String method;

	@Schema(description = "Request URI")
	private String uri;

	@Schema(description = "Firebase Cloud Messaging Server Key")
	private String fcmServerKey;

	@Schema(description = "Filter categories")
	private List<String> categories;

	@Schema(description = "Enabled flag")
	private boolean enabled;

	public Webhook() {
		// for serialisation/deserialization
	}

	public Webhook(Builder builder) {
		id = builder.id;
		appId = builder.appId;
		type = builder.type;
		method = builder.method;
		uri = builder.uri;
		fcmServerKey = builder.fcmServerKey;
		categories = builder.categories;
		enabled = builder.enabled;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Webhook copy) {
		return newBuilder()
				.setId(copy.getId())
				.setAppId(copy.getAppId())
				.setType(copy.getType())
				.setMethod(copy.getMethod())
				.setUri(copy.getUri())
				.setFcmServerKey(copy.getFcmServerKey())
				.setCategories(copy.getCategories())
				.setEnabled(copy.isEnabled());
	}

	public Long getId() {
		return id;
	}

	public long getAppId() {
		return appId;
	}

	public WebhookMessageType getType() {
		return type;
	}

	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getFcmServerKey() {
		return fcmServerKey;
	}

	public List<String> getCategories() {
		return categories;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public static final class Builder {
		private Long id;
		private long appId;
		private WebhookMessageType type;
		private String method;
		private String uri;
		private String fcmServerKey;
		private List<String> categories;
		private boolean enabled;

		private Builder() {
			categories = new ArrayList<>();
		}

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setAppId(long appId) {
			this.appId = appId;
			return this;
		}

		public Builder setType(WebhookMessageType type) {
			this.type = type;
			return this;
		}

		public Builder setMethod(String method) {
			this.method = method;
			return this;
		}

		public Builder setUri(String uri) {
			this.uri = uri;
			return this;
		}

		public Builder setFcmServerKey(String fcmServerKey) {
			this.fcmServerKey = fcmServerKey;
			return this;
		}

		public Builder setCategories(List<String> categories) {
			this.categories = categories;
			return this;
		}

		public Builder setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Webhook build() {
			return new Webhook(this);
		}
	}
}
