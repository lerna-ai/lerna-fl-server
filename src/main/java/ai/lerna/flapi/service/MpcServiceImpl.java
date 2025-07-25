package ai.lerna.flapi.service;

import ai.lerna.flapi.service.dto.MpcRequest;
import ai.lerna.flapi.service.dto.MpcResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MpcServiceImpl implements MpcService {
  XmlMapper mapper;
  private final MpcSocketFactory mpcSocketFactory;

  @Autowired
  public MpcServiceImpl(MpcSocketFactory mpcSocketFactory) {
    this.mpcSocketFactory = mpcSocketFactory;
    this.mapper = new XmlMapper();
  }

  public MpcResponse getLernaNoise(String host, int port, long jobId, ArrayList<Long> uIds) {
    return makeRequest(host, port, getMpcRequest(jobId, uIds));
  }

  public MpcResponse getLernaJob(String host, int port, BigDecimal epsilon, int dimensions, BigDecimal normalization) {
    return makeRequest(host, port, getInitialMpcRequest(epsilon, dimensions, normalization));
  }

  private MpcResponse makeRequest(String host, int port, MpcRequest mpcRequest) {
    try {
      Socket connection = mpcSocketFactory.getSocketFactory().createSocket(host, port);
      PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
      out.println(mapper.writeValueAsString(mpcRequest));
      out.flush();

      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String message = input.readLine();

      input.close();
      out.close();
      connection.close();

      Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MPC Response: {0}", message);

      return mapper.readValue(message, MpcResponse.class);
    } catch (IOException ex) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "MPC Request failed.", ex);
      return null;
    }
  }

  private MpcRequest getMpcRequest(long jobId, ArrayList<Long> uIds) {
    MpcRequest mpcRequest = new MpcRequest();
    mpcRequest.setCompId(jobId);
    mpcRequest.setDrop(uIds.stream().map(String::valueOf).collect(Collectors.joining(";")));
    return mpcRequest;
  }

  private MpcRequest getInitialMpcRequest(BigDecimal epsilon, int dimensions, BigDecimal normalization) {
    MpcRequest mpcRequest = new MpcRequest();
    mpcRequest.setCompId(0);
    mpcRequest.setMpc(true);
    mpcRequest.setEpsilon(epsilon);
    mpcRequest.setDimensions(dimensions);
    mpcRequest.setNormalization(normalization);
    return mpcRequest;
  }
}
