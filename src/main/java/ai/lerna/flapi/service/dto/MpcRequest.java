package ai.lerna.flapi.service.dto;

import ai.lerna.flapi.service.serialize.BigDecimalSerialize;
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
  private BigDecimal epsilon;

  @JacksonXmlProperty(localName = "Wsize")
  private Integer dimensions;

  @JacksonXmlProperty(localName = "a")
  @JsonSerialize(using = BigDecimalSerialize.class)
  private BigDecimal normalization;

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
    return epsilon;
  }

  public void setDifferentialPrivacy(BigDecimal epsilon) {
    this.epsilon = epsilon;
  }

  public Integer getDimensions() {
    return dimensions;
  }

  public void setDimensions(Integer dimensions) {
    this.dimensions = dimensions;
  }

  public BigDecimal getNormalization() {
    return normalization;
  }

  public void setNormalization(BigDecimal normalization) {
    this.normalization = normalization;
  }

  public String getDrop() {
    return drop;
  }

  public void setDrop(String drop) {
    this.drop = drop;
  }
}
