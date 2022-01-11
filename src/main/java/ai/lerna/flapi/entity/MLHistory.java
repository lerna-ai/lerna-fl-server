package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.INDArrayConverter;
import ai.lerna.flapi.entity.converter.MLHistoryWeightsConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ml_history")
public class MLHistory {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "ml_id")
	private long MLId;

	@Column(name = "version")
	private long version;

	@Column(name = "weights", columnDefinition = "jsonb")
	@Convert(converter = MLHistoryWeightsConverter.class)
	private Set<MLHistoryWeights> weights = new HashSet<>();

	@Column(name = "accuracy_avg")
	private BigDecimal accuracyAvg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMLId() {
		return MLId;
	}

	public void setMLId(long MLId) {
		this.MLId = MLId;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Set<MLHistoryWeights> getWeights() {
		return weights;
	}

	public void setWeights(Set<MLHistoryWeights> weights) {
		this.weights = weights;
	}

	public BigDecimal getAccuracyAvg() {
		return accuracyAvg;
	}

	public void setAccuracyAvg(BigDecimal accuracyAvg) {
		this.accuracyAvg = accuracyAvg;
	}
}
