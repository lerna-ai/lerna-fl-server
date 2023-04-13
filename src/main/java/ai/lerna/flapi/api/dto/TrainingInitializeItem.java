package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Model object with discovered classes")
public class TrainingInitializeItem {

	@Schema(description = "Model name")
	String modelName;

	@Schema(description = "List of discovered classes")
	List<String> jobs;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<String> getJobs() {
		return jobs;
	}

	public void setJobs(List<String> jobs) {
		this.jobs = jobs;
	}
}
