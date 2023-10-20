package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;

public class EngineDataset implements Serializable {
	private String ttl;

	EngineDataset() {
		// for serialisation/deserialization
	}

	private EngineDataset(Builder builder) {
		ttl = builder.ttl;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(EngineDataset copy) {
		return newBuilder()
				.setTtl(copy.getTtl());
	}

	public String getTtl() {
		return ttl;
	}

	public static final class Builder {
		private String ttl;

		private Builder() {
		}

		public Builder setTtl(String ttl) {
			this.ttl = ttl;
			return this;
		}

		public EngineDataset build() {
			return new EngineDataset(this);
		}
	}
}
