package ai.lerna.flapi.api.dto;

import java.io.Serializable;

public class TrainingInference implements Serializable {

	private long ml_id;
	private String model;
	private String prediction;

	public long getMl_id() {
		return ml_id;
	}

	public void setMl_id(long ml_id) {
		this.ml_id = ml_id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
}
