package ai.lerna.flapi.service;

import ai.lerna.flapi.service.dto.MpcResponse;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface MpcService {
  MpcResponse getLernaNoise(String host, int port, int jobId, ArrayList<Integer> u_ids);
  MpcResponse getLernaJob(String host, int port, BigDecimal differentialPrivacy, int weightSize, BigDecimal alpha);
}

