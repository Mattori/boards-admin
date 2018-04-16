package boards.models;

import com.google.gson.annotations.*;

public class Developer extends Model{
	@Expose
	private String identity;
	
	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
