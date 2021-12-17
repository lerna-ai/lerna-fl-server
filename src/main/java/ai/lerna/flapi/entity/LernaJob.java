package ai.lerna.flapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "lerna_job")
public class LernaJob {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;

	@Column(name = "ml_id")
	private long MLId;

	@Column(name = "model")
	private String model;

//	@Column(name = "weights")
//	private List<BigDecimal> weights;

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

//	public List<BigDecimal> getWeights() {
//		return weights;
//	}
//
//	public void setWeights(List<BigDecimal> weights) {
//		this.weights = weights;
//	}

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
