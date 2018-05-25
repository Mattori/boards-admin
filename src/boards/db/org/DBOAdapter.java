package boards.db.org;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import boards.models.org.Model;

public class DBOAdapter {
	private static Gson gson;

	private static Gson getGson() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			//gsonBuilder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
			gsonBuilder.excludeFieldsWithoutExposeAnnotation().setDateFormat("MMMM dd, yyyy HH:mm:ss");
			gson = gsonBuilder.create();
		}
		return gson;
	}

	public static <T extends Model> T dboToModel(DBObject dbObject, Class<T> clazz) {
		gson = getGson();
		String json = gson.toJson(dbObject);
		return gson.fromJson(json, clazz);
	}

	public static BasicDBObject objectToDBObject(Object object) {
		BasicDBObject obj = (BasicDBObject) JSON.parse(getGson().toJson(object));
		return obj;
	}

	public static DBObject[] objectToDBObjectArray(Object object) {
		BasicDBObject[] objects = new BasicDBObject[] { objectToDBObject(object) };
		return objects;
	}
}
