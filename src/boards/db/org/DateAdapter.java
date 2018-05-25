package boards.db.org;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateAdapter
		implements JsonSerializer<Date>, JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Date d = null;
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			d = f2.parse(json.getAsJsonObject().get("$date").getAsString());
		} catch (ParseException e) {
			d = null;
		}
		return d;
	}

	@Override
	public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context) {
		Date d = (Date) date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		JsonObject jo = new JsonObject();
		jo.addProperty("$date", format.format(d));
		return jo;
	}

}
