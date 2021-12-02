package ai.lerna.flapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiImpl implements TestApi {

  @GetMapping("")
  public String index() {
    return "FL API";
  }

}