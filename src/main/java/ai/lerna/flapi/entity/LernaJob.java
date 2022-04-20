package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.INDArrayConverter;
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lerna_job")
public class LernaJob {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "ml_id")
	private long MLId;

	@Column(name = "prediction")
	private String prediction;

	@Column(name = "prediction_value")
	private long predictionValue;

	@Column(name = "weights")
	@Convert(converter = INDArrayConverter.class)
	private INDArray weights;

	@Column(name = "total_data_points")
	private long totalDataPoints;

	@Column(name = "total_devices")
	private long totalDevices;

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

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public long getPredictionValue() {
		return predictionValue;
	}

	public void setPredictionValue(long predictionValue) {
		this.predictionValue = predictionValue;
	}

	public INDArray getWeights() {
		return weights;
	}

	public void setWeights(INDArray weights) {
		this.weights = weights;
	}

	public long getTotalDatapoints() {
		return totalDataPoints;
	}

	public void setTotalDataPoints(long totalDataPoints) {
		this.totalDataPoints = totalDataPoints;
	}

	public long getTotalDevices() {
		return totalDevices;
	}

	public void setTotalDevices(long totalDevices) {
		this.totalDevices = totalDevices;
	}

}
