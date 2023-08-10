package ai.lerna.flapi.service.actionML;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;

import java.lang.reflect.Type;


public class DateTimeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

	public DateTime deserialize(
			final JsonElement json,
			final Type type,
			final JsonDeserializationContext context
	) throws JsonParseException {
		return new DateTime(json.getAsJsonPrimitive().getAsString());
	}

	public JsonElement serialize(
			DateTime src,
			Type type,
			JsonSerializationContext context
	) {
		return new JsonPrimitive(src.toString());
	}
}
