package ai.lerna.flapi.api;

import ai.lerna.flapi.entity.MpcResponse;
import ai.lerna.flapi.service.MpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
public class TestApiImpl implements TestApi {
  static final BigDecimal eps = BigDecimal.ZERO;
  static final int d = 5;
  static final int iter = 30;
  static final int epoch = 5;
  static final BigDecimal alpha = BigDecimal.ONE;
  static int users = 2;

  private MpcService mpcService;

  @Autowired
  public TestApiImpl(MpcService mpcService) {
    this.mpcService = mpcService;
  }

  @GetMapping("")
  public String index() {
    return "FL API";
  }

  @GetMapping("/lerna")
  public MpcResponse lerna() {
    return mpcService.getLernaJob("localhost", 31337, eps, d, alpha);
  }

  @GetMapping("/lerna/{jobId}")
  public MpcResponse lernaByJob(@PathVariable int jobId) {
    return mpcService.getLernaNoise("localhost", 31337, jobId, new ArrayList<>());
  }

}