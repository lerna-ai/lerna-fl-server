package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EngineAlgorithm implements Serializable {
	private List<EngineAlgorithmIndicator> indicators;
	private List<EngineAlgorithmRanking> rankings;

	EngineAlgorithm() {
		// for serialisation/deserialization
	}

	private EngineAlgorithm(Builder builder) {
		indicators = builder.indicators;
		rankings = builder.rankings;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(EngineAlgorithm copy) {
		return newBuilder()
				.setIndicators(copy.getIndicators())
				.setRankings(copy.getRankings());
	}

	public List<EngineAlgorithmIndicator> getIndicators() {
		return indicators;
	}

	public List<EngineAlgorithmRanking> getRankings() {
		return rankings;
	}

	public static final class Builder {
		private List<EngineAlgorithmIndicator> indicators;
		private List<EngineAlgorithmRanking> rankings;

		private Builder() {
			indicators = new ArrayList<>();
			rankings = new ArrayList<>();
		}

		public Builder setIndicators(List<EngineAlgorithmIndicator> indicators) {
			this.indicators = indicators;
			return this;
		}

		public Builder setIndicatorsByList(List<String> indicators) {
			this.indicators = indicators.stream()
					.map(indicator -> EngineAlgorithmIndicator.newBuilder().setName(indicator).build())
					.collect(Collectors.toList());
			return this;
		}

		public Builder setRankings(List<EngineAlgorithmRanking> rankings) {
			this.rankings = rankings;
			return this;
		}

		public EngineAlgorithm build() {
			return new EngineAlgorithm(this);
		}
	}
}
