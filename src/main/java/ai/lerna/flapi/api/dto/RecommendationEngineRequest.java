package ai.lerna.flapi.api.dto;

import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Lerna Recommendation Engine Configuration")
public class RecommendationEngineRequest implements Serializable {

	@Schema(description = "Engine ID")
	private String engineId;

	@Schema(description = "Description")
	private String comment;

	@Schema(description = "Time to Live period for dataset")
	private String datasetTtl;


	@Schema(description = "An array of string identifiers describing Events recorded for users, things like “buy”, “watch”, “add-to-cart”, “search-terms”, even “location”, or “device” can be considered indicators of user preference.")
	private List<String> algorithmIndicators;

	@Schema(description = "Types of events per item to compute the ranking. For \"popular\", the indicator/type must be a primary indicator of the chosen algorithm.")
	private String algorithmRankingType;

	@Schema(description = "This only applies to the popularity types. For instance to change from using conversions only to conversions plus item views you can specify a “popular” ranking type with “indicatorNames”: [\"buy\", \"view\"]. The indicators/events that contribute to “popular” must have the same item ids as the primary indicator/event so using something like “search-terms” or “category-preference” would not be appropriate since the targetEntityId for those events is not the same as the primary event (buy, watch, listen, or other primary event).")
	private List<String> algorithmRankingIndicatorNames;

	@Schema(description = "Defines how far back in time the ranking algorithm checks for events in order to compute the item ranking.")
	private String algorithmRankingDuration;

	RecommendationEngineRequest() {
		// for serialisation/deserialization
	}

	private RecommendationEngineRequest(Builder builder) {
		engineId = builder.engineId;
		comment = builder.comment;
		datasetTtl = builder.datasetTtl;
		algorithmIndicators = builder.algorithmIndicators;
		algorithmRankingType = builder.algorithmRankingType;
		algorithmRankingIndicatorNames = builder.algorithmRankingIndicatorNames;
		algorithmRankingDuration = builder.algorithmRankingDuration;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(RecommendationEngineRequest copy) {
		return newBuilder()
				.setEngineId(copy.getEngineId())
				.setComment(copy.getComment())
				.setDatasetTtl(copy.getDatasetTtl())
				.setAlgorithmIndicators(copy.getAlgorithmIndicators())
				.setAlgorithmRankingType(copy.getAlgorithmRankingType())
				.setAlgorithmRankingIndicatorNames(copy.getAlgorithmRankingIndicatorNames())
				.setAlgorithmRankingDuration(copy.getAlgorithmRankingDuration());
	}

	public String getEngineId() {
		return engineId;
	}

	public String getComment() {
		return comment;
	}

	public String getDatasetTtl() {
		return datasetTtl;
	}

	public List<String> getAlgorithmIndicators() {
		return algorithmIndicators;
	}

	public String getAlgorithmRankingType() {
		return algorithmRankingType;
	}

	public List<String> getAlgorithmRankingIndicatorNames() {
		return algorithmRankingIndicatorNames;
	}

	public String getAlgorithmRankingDuration() {
		return algorithmRankingDuration;
	}

	@Override
	public String toString() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		return gsonBuilder.create().toJson(this);
	}

	public static final class Builder {
		private String engineId;
		private String comment;
		private String datasetTtl;
		private List<String> algorithmIndicators;
		private String algorithmRankingType;
		private List<String> algorithmRankingIndicatorNames;
		private String algorithmRankingDuration;

		private Builder() {
			algorithmIndicators = new ArrayList<>();
			algorithmRankingIndicatorNames = new ArrayList<>();
		}

		public Builder setEngineId(String engineId) {
			this.engineId = engineId;
			return this;
		}

		public Builder setComment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder setDatasetTtl(String datasetTtl) {
			this.datasetTtl = datasetTtl;
			return this;
		}

		public Builder setAlgorithmIndicators(List<String> algorithmIndicators) {
			this.algorithmIndicators = algorithmIndicators;
			return this;
		}

		public Builder setAlgorithmRankingType(String algorithmRankingType) {
			this.algorithmRankingType = algorithmRankingType;
			return this;
		}

		public Builder setAlgorithmRankingIndicatorNames(List<String> algorithmRankingIndicatorNames) {
			this.algorithmRankingIndicatorNames = algorithmRankingIndicatorNames;
			return this;
		}

		public Builder setAlgorithmRankingDuration(String algorithmRankingDuration) {
			this.algorithmRankingDuration = algorithmRankingDuration;
			return this;
		}

		public RecommendationEngineRequest build() {
			return new RecommendationEngineRequest(this);
		}
	}
}
