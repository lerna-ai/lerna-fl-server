package ai.lerna.flapi.api;

import ai.lerna.flapi.entity.LernaPrediction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(WebApi.path)
@Tag(name = WebApi.tag)
public interface WebApi {
	String path = "/api/v1/web";
	String tag = "Lerna WEB API";
	
	@Operation(summary = "Check Inference")
	@GetMapping("/inference")
	List<LernaPrediction> getInference(@RequestParam(value = "token") String token) throws Exception;
}
