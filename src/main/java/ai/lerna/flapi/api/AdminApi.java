package ai.lerna.flapi.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(AdminApi.path)
@Tag(name = AdminApi.tag)
public interface AdminApi {
	String path = "/api/v1/admin";
	String tag = "Lerna Admin API";

	@Operation(summary = "Persist storage on redis")
	@GetMapping("/storage/persist")
	String storagePersist(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Retrieve storage from redis")
	@GetMapping("/storage/retrieve")
	String storageRetrieve(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Cleanup redis stored values")
	@GetMapping("/storage/cleanup")
	String storageCleanup(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Prepare new trainings")
	@GetMapping("/training/prepare")
	String prepareNewTraining(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Replace jobID and update redis stored values")
	@GetMapping("/training/job/replace/all")
	String replaceAllJobs(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Replace jobIDs of selected app and update redis stored values")
	@GetMapping("/training/job/replace/{appToken}")
	String replaceJob(@RequestParam(value = "token") String token, @PathVariable(value = "appToken") String appToken) throws Exception;

	@Operation(summary = "Cleanup non-relative device weights of selected app")
	@GetMapping("/training/deviceWeights/cleanup/{appToken}")
	String cleanupDeviceWeights(@RequestParam(value = "token") String token, @PathVariable(value = "appToken") String appToken) throws Exception;
	
	@Operation(summary = "Check if enough users are gathered and run the FL for a specific token")
	@GetMapping("/training/runFL/{appToken}")
	String checkAndAggregate(@RequestParam(value = "token") String token, @PathVariable(value = "appToken") String appToken) throws Exception;
	
	@Operation(summary = "Check if enough users are gathered and run the FL")
	@GetMapping("/training/runFL/all")
	String checkAndAggregateAll(@RequestParam(value = "token") String token) throws Exception;
}
