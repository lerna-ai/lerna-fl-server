package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.serialize.BigDecimalSerialize;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;

@JacksonXmlRootElement(localName = "Body")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MpcRequest {

  @JacksonXmlProperty(localName = "CompID")
  private int compId;

  @JacksonXmlProperty(localName = "MPC")
  private Boolean mpc;

  @JacksonXmlProperty(localName = "DP")
  @JsonSerialize(using = BigDecimalSerialize.class)
  private BigDecimal differentialPrivacy;

  @JacksonXmlProperty(localName = "Wsize")
  private Integer weightSize;

  @JacksonXmlProperty(localName = "a")
  @JsonSerialize(using = BigDecimalSerialize.class)
  private BigDecimal alpha;

  @JacksonXmlProperty(localName = "Drop")
  private String drop;

  public int getCompId() {
    return compId;
  }

  public void setCompId(int compId) {
    this.compId = compId;
  }

  public Boolean getMpc() {
    return mpc;
  }

  public void setMpc(Boolean mpc) {
    this.mpc = mpc;
  }

  public BigDecimal getDifferentialPrivacy() {
    return differentialPrivacy;
  }

  public void setDifferentialPrivacy(BigDecimal differentialPrivacy) {
    this.differentialPrivacy = differentialPrivacy;
  }

  public Integer getWeightSize() {
    return weightSize;
  }

  public void setWeightSize(Integer weightSize) {
    this.weightSize = weightSize;
  }

  public BigDecimal getAlpha() {
    return alpha;
  }

  public void setAlpha(BigDecimal alpha) {
    this.alpha = alpha;
  }

  public String getDrop() {
    return drop;
  }

  public void setDrop(String drop) {
    this.drop = drop;
  }
}
