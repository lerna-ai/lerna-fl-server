package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.INDArrayConverter;
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.persistence.Convert;
import java.io.Serializable;

public class MLHistoryWeights implements Serializable {
	private String prediction;

	@Convert(converter = INDArrayConverter.class)
	private INDArray weights;

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public INDArray getWeights() {
		return weights;
	}

	public void setWeights(INDArray weights) {
		this.weights = weights;
	}
}
