package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;

public class EngineAlgorithmIndicator implements Serializable {
	private String name;

	EngineAlgorithmIndicator() {
		// for serialisation/deserialization
	}

	private EngineAlgorithmIndicator(Builder builder) {
		name = builder.name;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(EngineAlgorithmIndicator copy) {
		return newBuilder()
				.setName(copy.getName());
	}

	public String getName() {
		return name;
	}

	public static final class Builder {
		private String name;

		private Builder() {
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public EngineAlgorithmIndicator build() {
			return new EngineAlgorithmIndicator(this);
		}
	}
}
