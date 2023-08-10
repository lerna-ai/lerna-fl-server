package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(description = "Lerna recommendation item with score and properties")
public class RecommendationItemScore implements Serializable {

	@Schema(description = "Item name")
	private String item;

	@Schema(description = "Item score")
	private Double score;

	@Schema(description = "Item properties")
	private Map<String, List<String>> props;

	public RecommendationItemScore() {
		// for serialisation/deserialization
	}

	public RecommendationItemScore(Builder builder) {
		item = builder.item;
		score = builder.score;
		props = builder.props;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(RecommendationItemScore copy) {
		return newBuilder()
				.setItem(copy.item)
				.setScore(copy.score)
				.setProps(copy.props);
	}

	public String getItem() {
		return item;
	}

	public Double getScore() {
		return score;
	}

	public Map<String, List<String>> getProps() {
		return props;
	}


	public static final class Builder {
		private String item;
		private Double score;
		private Map<String, List<String>> props;

		private Builder() {
			props = new HashMap<>();
		}

		public Builder setItem(String item) {
			this.item = item;
			return this;
		}

		public Builder setScore(Double score) {
			this.score = score;
			return this;
		}

		public Builder setProps(Map<String, List<String>> props) {
			this.props = props;
			return this;
		}

		public RecommendationItemScore build() {
			return new RecommendationItemScore(this);
		}
	}
}
