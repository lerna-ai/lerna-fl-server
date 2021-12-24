package ai.lerna.flapi.api;

import ai.lerna.flapi.service.dto.MpcResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/array/{d}")
	INDArray getINDArray(@PathVariable double d);
}