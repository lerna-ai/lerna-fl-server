package ai.lerna.flapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author gkellaris
 */
public class LernaMLParameters implements Serializable {
	@Schema(description = "Normalization value alpha or lambda to avoid overfitting (0.05, 0.1, 0.5, 1.0, 5.0 etc.)")
	private BigDecimal normalization;

	@Schema(description = "Number of local iterations")
	private int iterations;

//	@Schema(description = "Threshold value for the termination/convergence of SGD, usually 0.0001")
//	private BigDecimal thres;

	@Schema(description = "The learning rate, usually 0.05")
	private BigDecimal learningRate;

	@Schema(description = "The number of features/weights")
	private int dimensions;

	@Schema(description = "The percentage of test/train data")
	private BigDecimal dataSplit;

	public BigDecimal getNormalization() {
		return normalization;
	}

	public void setNormalization(BigDecimal normalization) {
		this.normalization = normalization;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

//    public BigDecimal getThres() {
//        return thres;
//    }
//
//    public void setThres(BigDecimal thres) {
//        this.thres = thres;
//    }

	public BigDecimal getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(BigDecimal learningRate) {
		this.learningRate = learningRate;
	}

	public int getDimensions() {
		return dimensions;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public BigDecimal getDataSplit() {
		return dataSplit;
	}

	public void setDataSplit(BigDecimal dataSplit) {
		this.dataSplit = dataSplit;
	}
}
