package ai.lerna.flapi.service.actionML.dto;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class Engine implements Serializable {
	private String engineId;
	private String engineFactory;
	private String comment;
	private EngineDataset dataset;
	private EngineSparkConf sparkConf;
	private EngineAlgorithm algorithm;

	Engine() {
		// for serialisation/deserialization
	}

	private Engine(Builder builder) {
		engineId = builder.engineId;
		engineFactory = builder.engineFactory;
		comment = builder.comment;
		dataset = builder.dataset;
		sparkConf = builder.sparkConf;
		algorithm = builder.algorithm;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Engine copy) {
		return newBuilder()
				.setEngineId(copy.getEngineId())
				.setEngineFactory(copy.getEngineFactory())
				.setComment(copy.getComment())
				.setDataset(copy.getDataset())
				.setSparkConf(copy.getSparkConf())
				.setAlgorithm(copy.getAlgorithm());
	}

	public String getEngineId() {
		return engineId;
	}

	public String getEngineFactory() {
		return engineFactory;
	}

	public String getComment() {
		return comment;
	}

	public EngineDataset getDataset() {
		return dataset;
	}

	public EngineSparkConf getSparkConf() {
		return sparkConf;
	}

	public EngineAlgorithm getAlgorithm() {
		return algorithm;
	}

	@Override
	public String toString() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		return gsonBuilder.create().toJson(this);
	}

	public static final class Builder {
		private String engineId;
		private String engineFactory;
		private String comment;
		private EngineDataset dataset;
		private EngineSparkConf sparkConf;
		private EngineAlgorithm algorithm;

		private Builder() {
			dataset = EngineDataset.newBuilder().build();
			sparkConf = EngineSparkConf.newBuilderDefaultValues().build();
			algorithm = EngineAlgorithm.newBuilder().build();
		}

		public Builder setEngineId(String engineId) {
			this.engineId = engineId;
			return this;
		}

		public Builder setEngineFactory(String engineFactory) {
			this.engineFactory = engineFactory;
			return this;
		}

		public Builder setComment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder setDataset(EngineDataset dataset) {
			this.dataset = dataset;
			return this;
		}

		public Builder setSparkConf(EngineSparkConf sparkConf) {
			this.sparkConf = sparkConf;
			return this;
		}

		public Builder setAlgorithm(EngineAlgorithm algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public Engine build() {
			return new Engine(this);
		}
	}
}
