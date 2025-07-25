package ai.lerna.flapi.service.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalSerialize extends JsonSerializer<BigDecimal> {
  @Override
  public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
    if (value != null && !"".equals(value)) {
      gen.writeString(value.setScale(1, BigDecimal.ROUND_HALF_DOWN) + "");
    } else {
      gen.writeString(value + "");
    }
  }
}
