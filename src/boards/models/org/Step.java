package boards.models.org;

import com.google.gson.annotations.Expose;

public class Step extends Model {
	@Expose
	private String title;

	public Step() {
		this(null, "NO TITLE");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Step(String id, String title) {
		super(id);
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
