package ai.lerna.flapi.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lerna_job")
public class LernaJob {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Column(name = "MLID")
    private long ml_id;
    
    @Column(name = "model")
    private String model;
    
    @Column(name = "weights")
    private List<BigDecimal> weights;
    
    @Column(name = "totalDataPoints")
    private long totalDataPoints;
    
    @Column(name = "totalDevices")
    private long totalDevices;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMLId() {
        return ml_id;
    }

    public void setMLId(long ml_id) {
        this.ml_id = ml_id;
    }
    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    
    public List<BigDecimal> getWeights() {
        return weights;
    }

    public void setWeights(List<BigDecimal> weights) {
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
