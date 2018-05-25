package boards.db.org;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import boards.models.org.Model;

public class MyMongo {
	protected DB db;
	protected MongoClient mongoClient;
	private DBCollection collection;

	public DB getDb() {
		return db;
	}

	public boolean connect(String dbname) throws UnknownHostException {
		return this.connect(dbname, "127.0.0.1", 27017);
	}

	public boolean connect(String dbname, String server, int port) throws UnknownHostException {
		mongoClient = new MongoClient(server, port);
		List<String> dbnames = mongoClient.getDatabaseNames();
		db = mongoClient.getDB(dbname);
		return dbnames.contains(dbname);
	}

	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}

	public WriteResult insert(String collectionName, Object object) {
		setCollection(collectionName);
		return insert(object);
	}

	public WriteResult insert(Object object) {
		return collection.insert(DBOAdapter.objectToDBObject(object));
	}

	public WriteResult save(String collectionName, Object object) {
		setCollection(collectionName);
		return save(object);
	}

	public WriteResult save(Object object) {
		return collection.save(DBOAdapter.objectToDBObject(object));
	}

	public DBObject findOne() {
		return collection.findOne();
	}

	public DBObject findOne(String collectionName) {
		setCollection(collectionName);
		return findOne();
	}

	public DBObject findOne(BasicDBObject query) {
		return collection.findOne(query);
	}

	public DBObject findOne(String collectionName, BasicDBObject query) {
		setCollection(collectionName);
		return findOne(query);
	}
	
	public WriteResult remove(Object object) {
		DBObject dbo=DBOAdapter.objectToDBObject(object);
		return collection.remove(dbo);
	}

	/**
	 * Retourne tous les documents de la collection
	 * 
	 * @param collectionName
	 * @return
	 */
	public Cursor find(String collectionName) {
		setCollection(collectionName);
		return find();
	}

	public Cursor find() {
		return collection.find();
	}

	public WriteResult insert(String collectionName, List<? extends Model> objects) {
		setCollection(collectionName);
		BasicDBObject[] dbList = new BasicDBObject[objects.size()];
		int i = 0;
		for (Model m : objects) {
			dbList[i++] = DBOAdapter.objectToDBObject(m);
		}
		return collection.insert(dbList);
	}

	public void save(String collectionName, List<? extends Model> objects) {
		setCollection(collectionName);
		for (Model m : objects) {
			collection.save(DBOAdapter.objectToDBObject(m));
		}
	}

	public Cursor find(String collectionName, BasicDBObject query) {
		setCollection(collectionName);
		return find(query);
	}

	public <T extends Model> List<T> load(Cursor cursor, Class<T> clazz) {
		setCollection(clazz.getSimpleName());
		List<T> result = new ArrayList<>();
		while (cursor.hasNext()) {
			try {
				result.add(DBOAdapter.dboToModel(cursor.next(), clazz));
			} catch(Exception e) {
				
			}
		}
		return result;
	}

	public <T extends Model> List<T> load(BasicDBObject query, Class<T> clazz) {
		return load(find(query), clazz);
	}

	public <T extends Model> List<T> load(Class<T> clazz) {
		return load(find(clazz.getSimpleName()), clazz);
	}

	public Cursor find(BasicDBObject query) {
		return collection.find(query);
	}

	public void setCollection(String name) {
		name = name.toLowerCase() + "s";
		collection = db.getCollection(name);
	}

	public List<String> getDbNames() {
		return mongoClient.getDatabaseNames();
	}

	public void close() {
		mongoClient.close();
	}

	public void dropCollection(String name) {
		this.setCollection(name);
		collection.drop();
	}

	public void dropCollection(Class<? extends Model> clazz) {
		this.dropCollection(clazz.getSimpleName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dropCollections(Class... classes) {
		for (Class clazz : classes) {
			dropCollection(clazz);
		}
	}
}
