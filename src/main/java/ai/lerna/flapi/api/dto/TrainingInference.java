package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Training Inference object")
public class TrainingInference implements Serializable {

	@Schema(description = "ML Model ID")
	private long ml_id;

	@Schema(description = "ML Model Name")
	private String model;

	@Schema(description = "Predicted value")
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
