package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Webhook configurations")
public class WebhookResponse {

	@Schema(description = "List of Webhooks configuration")
	private List<Webhook> webhooks;

	public WebhookResponse() {
		// for serialisation/deserialization
	}

	public WebhookResponse(Builder builder) {
		webhooks = builder.webhooks;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(WebhookResponse copy) {
		return newBuilder()
				.setWebhooks(copy.getWebhooks());
	}

	public List<Webhook> getWebhooks() {
		return webhooks;
	}

	public static final class Builder {
		private List<Webhook> webhooks;

		private Builder() {
			webhooks = new ArrayList<>();
		}

		public Builder setWebhooks(List<Webhook> webhooks) {
			this.webhooks = webhooks;
			return this;
		}

		public WebhookResponse build() {
			return new WebhookResponse(this);
		}
	}
}
