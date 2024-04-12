package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.service.actionML.DateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import org.joda.time.DateTime;

import java.io.Serializable;

@Schema(description = "Lerna Recommendation event")
public class RecommendationEvent implements Serializable {
	@Schema(description = "Engine ID")
	private String engineId;

	@Schema(description = "Event indicator, must be one of the \"indicators\" array from the engine's config.")
	private String event;

	@Schema(description = "The unique user ID")
	private String entityId;

	@Schema(description = "The ID for items that correspond to the indicator name. ")
	private String targetEntityId;

	@Schema(description = "The time at which the event takes place")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private DateTime eventTime;

	public RecommendationEvent() {
		// for serialisation/deserialization
	}

	public RecommendationEvent(Builder builder) {
		engineId = builder.engineId;
		event = builder.event;
		entityId = builder.entityId;
		targetEntityId = builder.targetEntityId;
		eventTime = builder.eventTime;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(RecommendationEvent copy) {
		return newBuilder()
				.setEngineId(copy.getEngineId())
				.setEvent(copy.getEvent())
				.setEntityId(copy.getEntityId())
				.setTargetEntityId(copy.getTargetEntityId())
				.setEventTime(copy.getEventTime());
	}

	public String getEngineId() {
		return engineId;
	}

	public String getEvent() {
		return event;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getTargetEntityId() {
		return targetEntityId;
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

	public String toCsv() {
		return engineId + ","
				+ event + ","
				+ entityId + ","
				+ targetEntityId + ","
				+ eventTime.toString("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}

	public static final class Builder {
		private String engineId;
		private String event;
		private String entityId;
		private String targetEntityId;
		private DateTime eventTime;

		private Builder() {
		}

		public Builder setEngineId(String engineId) {
			this.engineId = engineId;
			return this;
		}

		public Builder setEvent(String event) {
			this.event = event;
			return this;
		}

		public Builder setEntityId(String entityId) {
			this.entityId = entityId;
			return this;
		}

		public Builder setTargetEntityId(String targetEntityId) {
			this.targetEntityId = targetEntityId;
			return this;
		}

		public Builder setEventTime(DateTime eventTime) {
			this.eventTime = eventTime;
			return this;
		}

		public RecommendationEvent build() {
			return new RecommendationEvent(this);
		}
	}
}
