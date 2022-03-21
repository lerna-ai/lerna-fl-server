package ai.lerna.flapi.api;

import ai.lerna.flapi.service.dto.MpcResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping(TestApi.path)
@Tag(name = TestApi.tag)
public interface TestApi {
	String path = "/test";
	String tag = "Test API";

	@Operation(summary = "Get index page")
	@GetMapping("")
	String index();

	@Operation(summary = "Get Job ID")
	@GetMapping("/lerna")
	MpcResponse lerna();

	@Operation(summary = "Get Noise Shares for selected Job ID")
	@GetMapping("/lerna/{jobId}")
	MpcResponse lernaByJob(@PathVariable int jobId);

	@Operation(summary = "Get Jobs-Weights map for selected token")
	@GetMapping("/jobs")
	Map<String, String> jobsById(@RequestParam(value = "token") String token);

	@GetMapping("/array/{d}")
	INDArray getINDArray(@PathVariable double d);

	@GetMapping("/array/size/{rows}")
	INDArray getINDArraySized(@PathVariable long rows);

	@PostMapping("/array/inspect")
	String inspectArray(@RequestBody INDArray array) throws Exception;
}