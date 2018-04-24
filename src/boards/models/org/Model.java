package boards.models.org;

import java.util.UUID;


import com.google.gson.annotations.Expose;

public abstract class Model {
	@Expose
	protected String _id;

	public Model() {
		this(null);
	}

	public Model(String id) {
		this._id = id;
	}
	
	public String generateId() {
		_id = UUID.randomUUID().toString();
		return _id;
	}

	public String get_id() {
		return _id;
	}
}
