package ai.lerna.flapi.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;
import java.util.List;

@JacksonXmlRootElement(localName = "Body")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MpcResponse {

  @JacksonXmlProperty(localName = "CompID")
  private long compId;

  @JacksonXmlElementWrapper(localName = "Shares")
  @JacksonXmlProperty(localName = "Share")
  private List<BigDecimal> share;

  public long getCompId() {
    return compId;
  }

  public void setCompId(long compId) {
    this.compId = compId;
  }

  public List<BigDecimal> getShare() {
    return share;
  }

  public void setShare(List<BigDecimal> share) {
    this.share = share;
  }
}
