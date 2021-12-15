package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.entity.LernaMLParameters;
import io.swagger.v3.oas.annotations.media.Schema;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.Serializable;

@Schema(description = "Get individual noisy weights")
public class TrainingWeightsRequest implements Serializable {

    
        private long jobId;
        private int deviceId;
	private INDArray device_weights;
        
        public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
        
        public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
        
	public INDArray getDeviceWeights() {
		return device_weights;
	}

	public void setDeviceWeights(INDArray device_weights) {
		this.device_weights = device_weights;
	}

}
