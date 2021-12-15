package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.nd4j.linalg.api.ndarray.INDArray;
import java.io.Serializable;

@Schema(description = "Aggregated model weights")
public class TrainingWeightsResponse implements Serializable {

    
        private long jobId;
        private INDArray weights;
        
        public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
        
        public INDArray getWeights() {
		return weights;
	}

	public void setDeviceId(INDArray weights) {
		this.weights = weights.copy();
	}

}
