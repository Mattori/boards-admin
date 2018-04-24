package boards.models.org;

import org.bson.types.ObjectId;

import com.google.gson.annotations.Expose;

public class Tag extends Model {
	@Expose
	private String title;
	@Expose
	private String color;

	public Tag() {
		this(null, "NO TITLE", "");
	}

	public Tag(String id, String title, String color) {
		super(id);
		this.title = title;
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return title;
	}

}
