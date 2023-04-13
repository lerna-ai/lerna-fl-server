package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Get new training configuration")
public class TrainingInitialize implements Serializable {

	@Schema(description = "List of provided classes and discovered calls on each model")
	private List<TrainingInitializeItem> classes;

	public List<TrainingInitializeItem> getClasses() {
		return classes;
	}

	public void setClasses(List<TrainingInitializeItem> classes) {
		this.classes = classes;
	}
}
