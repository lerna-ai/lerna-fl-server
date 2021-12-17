package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(description = "New training configuration")
public class TrainingTaskResponse implements Serializable {

	private List<TrainingTask> trainingTasks;

	public List<TrainingTask> getTrainingTasks() {
		return trainingTasks;
	}

	public void setTrainingTasks(List<TrainingTask> trainingTasks) {
		this.trainingTasks = trainingTasks;
	}
}
