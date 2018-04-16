package boards.database;

import java.lang.reflect.Type;

import org.bson.types.ObjectId;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ObjectIdAdapter
implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {

	@Override
	public JsonElement serialize(ObjectId id, Type typeOfT, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("$oid", id.toHexString());
		return jo;
	}

	@Override
	public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		try {
		    return new ObjectId(json.getAsJsonObject().get("$oid").getAsString());
		} catch (Exception e) {
		    return null;
		}
	}

}