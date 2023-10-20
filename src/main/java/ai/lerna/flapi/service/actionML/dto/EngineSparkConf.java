package ai.lerna.flapi.service.actionML.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EngineSparkConf implements Serializable {
	private String master;

	@JsonProperty("spark.driver.memory")
	@SerializedName(value = "spark.driver.memory")
	private String driverMemory;

	@JsonProperty("spark.executor.memory")
	@SerializedName(value = "spark.executor.memory")
	private String executorMemory;

	@JsonProperty("spark.es.index.auto.create")
	@SerializedName(value = "spark.es.index.auto.create")
	private String esIndexAutoCreate;

	@JsonProperty("spark.es.nodes")
	@SerializedName(value = "spark.es.nodes")
	private String esNodes;

	@JsonProperty("spark.es.nodes.wan.only")
	@SerializedName(value = "spark.es.nodes.wan.only")
	private String esNodesWanOnly;

	@JsonProperty("spark.kryo.referenceTracking")
	@SerializedName(value = "spark.kryo.referenceTracking")
	private String kryoReferenceTracking;

	@JsonProperty("spark.kryo.registrator")
	@SerializedName(value = "spark.kryo.registrator")
	private String kryoRegistrator;

	@JsonProperty("spark.kryoserializer.buffer")
	@SerializedName(value = "spark.kryoserializer.buffer")
	private String kryoserializerBuffer;

	@JsonProperty("spark.serializer")
	@SerializedName(value = "spark.serializer")
	private String serializer;

	EngineSparkConf() {
		// for serialisation/deserialization
	}

	EngineSparkConf(Builder builder) {
		master = builder.master;
		driverMemory = builder.driverMemory;
		executorMemory = builder.executorMemory;
		esIndexAutoCreate = builder.esIndexAutoCreate;
		esNodes = builder.esNodes;
		esNodesWanOnly = builder.esNodesWanOnly;
		kryoReferenceTracking = builder.kryoReferenceTracking;
		kryoRegistrator = builder.kryoRegistrator;
		kryoserializerBuffer = builder.kryoserializerBuffer;
		serializer = builder.serializer;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(EngineSparkConf copy) {
		return newBuilder()
				.setMaster(copy.getMaster())
				.setDriverMemory(copy.getDriverMemory())
				.setExecutorMemory(copy.getExecutorMemory())
				.setEsIndexAutoCreate(copy.getEsIndexAutoCreate())
				.setEsNodes(copy.getEsNodes())
				.setEsNodesWanOnly(copy.getEsNodesWanOnly())
				.setKryoReferenceTracking(copy.getKryoReferenceTracking())
				.setKryoRegistrator(copy.getKryoRegistrator())
				.setKryoserializerBuffer(copy.getKryoserializerBuffer())
				.setSerializer(copy.getSerializer());
	}

	public static Builder newBuilderDefaultValues() {
		return newBuilder()
				.setMaster("local")
				.setDriverMemory("3g")
				.setExecutorMemory("3g")
				.setEsIndexAutoCreate("true")
				.setEsNodes("host.docker.internal")
				.setEsNodesWanOnly("true")
				.setKryoReferenceTracking("false")
				.setKryoRegistrator("org.apache.mahout.sparkbindings.io.MahoutKryoRegistrator")
				.setKryoserializerBuffer("300m")
				.setSerializer("org.apache.spark.serializer.KryoSerializer");
	}

	public String getMaster() {
		return master;
	}

	public String getDriverMemory() {
		return driverMemory;
	}

	public String getExecutorMemory() {
		return executorMemory;
	}

	public String getEsIndexAutoCreate() {
		return esIndexAutoCreate;
	}

	public String getEsNodes() {
		return esNodes;
	}

	public String getEsNodesWanOnly() {
		return esNodesWanOnly;
	}

	public String getKryoReferenceTracking() {
		return kryoReferenceTracking;
	}

	public String getKryoRegistrator() {
		return kryoRegistrator;
	}

	public String getKryoserializerBuffer() {
		return kryoserializerBuffer;
	}

	public String getSerializer() {
		return serializer;
	}

	public static final class Builder {
		private String master;
		private String driverMemory;
		private String executorMemory;
		private String esIndexAutoCreate;
		private String esNodes;
		private String esNodesWanOnly;
		private String kryoReferenceTracking;
		private String kryoRegistrator;
		private String kryoserializerBuffer;
		private String serializer;

		private Builder() {
		}

		public Builder setMaster(String master) {
			this.master = master;
			return this;
		}

		public Builder setDriverMemory(String driverMemory) {
			this.driverMemory = driverMemory;
			return this;
		}

		public Builder setExecutorMemory(String executorMemory) {
			this.executorMemory = executorMemory;
			return this;
		}

		public Builder setEsIndexAutoCreate(String esIndexAutoCreate) {
			this.esIndexAutoCreate = esIndexAutoCreate;
			return this;
		}

		public Builder setEsNodes(String esNodes) {
			this.esNodes = esNodes;
			return this;
		}

		public Builder setEsNodesWanOnly(String esNodesWanOnly) {
			this.esNodesWanOnly = esNodesWanOnly;
			return this;
		}

		public Builder setKryoReferenceTracking(String kryoReferenceTracking) {
			this.kryoReferenceTracking = kryoReferenceTracking;
			return this;
		}

		public Builder setKryoRegistrator(String kryoRegistrator) {
			this.kryoRegistrator = kryoRegistrator;
			return this;
		}

		public Builder setKryoserializerBuffer(String kryoserializerBuffer) {
			this.kryoserializerBuffer = kryoserializerBuffer;
			return this;
		}

		public Builder setSerializer(String serializer) {
			this.serializer = serializer;
			return this;
		}

		public EngineSparkConf build() {
			return new EngineSparkConf(this);
		}
	}
}
