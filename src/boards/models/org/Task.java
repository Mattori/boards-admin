package boards.models.org;

import com.google.gson.annotations.Expose;

public class Task extends Model {
	@Expose
	private String content;
	@Expose
	private Storie storie;
	@Expose
	private boolean closed;

	public Task() {
		this(null, "NO CONTENT");
	}

	public Task(String id, String content) {
		super(id);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Storie getStory() {
		return storie;
	}

	public void setStory(Storie storie) {
		this.storie = storie;
	}

	@Override
	public String toString() {
		return content;
	}

	public void close() {
		this.closed = true;

	}

}
