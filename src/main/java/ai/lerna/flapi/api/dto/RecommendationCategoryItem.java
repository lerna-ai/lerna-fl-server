package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.service.actionML.DateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Schema(description = "Lerna Recommendation item")
public class RecommendationCategoryItem implements Serializable {

	@Schema(description = "Unique Item ID")
	private String itemId;

	@Schema(description = "Engine ID")
	private String engineId;

	@Schema(description = "Map of event properties")
	private Map<String, Object> properties;

	@Schema(description = "The time at which the event takes place")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private DateTime eventTime;

	public RecommendationCategoryItem() {
		// for serialisation/deserialization
	}

	public RecommendationCategoryItem(Builder builder) {
		itemId = builder.itemId;
		engineId = builder.engineId;
		properties = builder.properties;
		eventTime = builder.eventTime;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(RecommendationCategoryItem copy) {
		return newBuilder()
				.setItemId(copy.getItemId())
				.setEngineId(copy.getEngineId())
				.setProperties(copy.getProperties())
				.setEventTime(copy.getEventTime());
	}

	public String getItemId() {
		return itemId;
	}

	public String getEngineId() {
		return engineId;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public DateTime getEventTime() {
		return eventTime;
	}

	@Override
	public String toString() {
		// handle DateTime separately
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeAdapter());
		Gson gson = gsonBuilder.create();
		return gson.toJson(this); // works when there are no generic types
	}

	public static final class Builder {
		private String itemId;
		private String engineId;
		private Map<String, Object> properties;
		private DateTime eventTime;

		private Builder() {
			properties = new HashMap<>();
		}

		public Builder setItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}

		public Builder setEngineId(String engineId) {
			this.engineId = engineId;
			return this;
		}

		public Builder setProperties(Map<String, Object> properties) {
			this.properties = properties;
			return this;
		}

		public Builder setEventTime(DateTime eventTime) {
			this.eventTime = eventTime;
			return this;
		}

		public RecommendationCategoryItem build() {
			return new RecommendationCategoryItem(this);
		}
	}
}
