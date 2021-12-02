package ai.lerna.flapi.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(TestApi.path)
public interface TestApi {
  String path = "/";

  @GetMapping("")
  String index();

}