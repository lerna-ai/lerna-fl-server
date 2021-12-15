package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "New training configuration")
public class TrainingInitResponse implements Serializable {

	private BigDecimal alpha;

	private int epoch;

	private int jobId;

	private BigDecimal precision;

	@Schema(description = "Determines the step size at each iteration while moving toward a minimum of a loss function", example = "0.02")
	private BigDecimal learningRate;

	private BigDecimal split;

	public BigDecimal getAlpha() {
		return alpha;
	}

	public void setAlpha(BigDecimal alpha) {
		this.alpha = alpha;
	}

	public int getEpoch() {
		return epoch;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public BigDecimal getPrecision() {
		return precision;
	}

	public void setPrecision(BigDecimal precision) {
		this.precision = precision;
	}

	public BigDecimal getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(BigDecimal learningRate) {
		this.learningRate = learningRate;
	}

	public BigDecimal getSplit() {
		return split;
	}

	public void setSplit(BigDecimal split) {
		this.split = split;
	}
}
