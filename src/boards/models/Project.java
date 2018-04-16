package boards.models;

import com.google.gson.annotations.*;

public class Project extends Model{
	@Expose
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
