package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EngineAlgorithmRanking implements Serializable {
	private String name;
	private String type;
	private List<String> indicatorNames;
	private String duration;

	EngineAlgorithmRanking() {
		// for serialisation/deserialization
	}

	private EngineAlgorithmRanking(Builder builder) {
		name = builder.name;
		type = builder.type;
		indicatorNames = builder.indicatorNames;
		duration = builder.duration;;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(EngineAlgorithmRanking copy) {
		return newBuilder()
				.setName(copy.getName())
				.setType(copy.getType())
				.setIndicatorNames(copy.getIndicatorNames())
				.setDuration(copy.getDuration());
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public List<String> getIndicatorNames() {
		return indicatorNames;
	}

	public String getDuration() {
		return duration;
	}

	public static final class Builder {
		private String name;
		private String type;
		private List<String> indicatorNames;
		private String duration;

		private Builder() {
			indicatorNames = new ArrayList<>();
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setType(String type) {
			this.type = type;
			return this;
		}

		public Builder setIndicatorNames(List<String> indicatorNames) {
			this.indicatorNames = indicatorNames;
			return this;
		}

		public Builder setDuration(String duration) {
			this.duration = duration;
			return this;
		}

		public EngineAlgorithmRanking build() {
			return new EngineAlgorithmRanking(this);
		}
	}
}
