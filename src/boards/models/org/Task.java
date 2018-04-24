package boards.models.org;

import org.bson.types.ObjectId;

import com.google.gson.annotations.Expose;

public class Task extends Model {
	@Expose
	private String content;
	@Expose
	private Story story;
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

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	@Override
	public String toString() {
		return content;
	}

	public void close() {
		this.closed = true;

	}

}
