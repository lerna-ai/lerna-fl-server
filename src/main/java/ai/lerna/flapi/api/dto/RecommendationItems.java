package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Lerna Recommendation response")
public class RecommendationItems implements Serializable {

	@Schema(description = "Recommendation Results")
	private List<RecommendationItemScore> result;

	public RecommendationItems() {
		// for serialisation/deserialization
	}

	public RecommendationItems(Builder builder) {
		result = builder.result;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(RecommendationItems copy) {
		return newBuilder()
				.setResult(copy.result);
	}

	public List<RecommendationItemScore> getResult() {
		return result;
	}


	public static final class Builder {
		private List<RecommendationItemScore> result;

		private Builder() {
			result = new ArrayList<>();
		}

		public Builder setResult(List<RecommendationItemScore> result) {
			this.result = result;
			return this;
		}

		public RecommendationItems build() {
			return new RecommendationItems(this);
		}
	}
}
