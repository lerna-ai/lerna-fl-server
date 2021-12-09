package ai.lerna.flapi.service

import ai.lerna.flapi.entity.MpcRequest
import ai.lerna.flapi.entity.MpcResponse
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import spock.lang.Specification

class MpcServiceSpec extends Specification {
  MpcServiceImpl mpcService;
  XmlMapper mapper;

  def setup() {
    mpcService = new MpcServiceImpl()
    mapper = new XmlMapper()
  }

  def "Should initial request map to correct xml"() {
    given:
      MpcRequest request = mpcService.getInitialMpcRequest(BigDecimal.ZERO, 5, BigDecimal.ONE)
    when:
      String result = mapper.writeValueAsString(request)
    then:
      result == "<Body><CompID>0</CompID><MPC>true</MPC><DP>0.0</DP><Wsize>5</Wsize><a>1.0</a></Body>"
  }

  def "Should map to correct xml"() {
    given:
      MpcRequest request = mpcService.getMpcRequest(1, [1,2,3])
    when:
      String result = mapper.writeValueAsString(request)
    then:
      result == "<Body><CompID>1</CompID><Drop>1;2;3</Drop></Body>"
  }

  def "Should response xml convert to correct object"() {
    given:
      String response = "<Body><CompID>4</CompID><Shares><Share>1.0</Share><Share>0.0</Share><Share>2.3</Share></Shares></Body>"
    when:
      MpcResponse result = mapper.readValue(response, MpcResponse.class)
    then:
      with(result) {
        compId == 4
        share.size() == 3
        with(share) {
          get(0) == BigDecimal.ONE
          get(1) == BigDecimal.ZERO
          get(2) == BigDecimal.valueOf(2.3)
        }
      }
  }
}
