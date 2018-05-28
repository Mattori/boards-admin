package boards.models.org;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.annotations.Expose;

public class Developer extends Model {
	@Expose
	private String identity;
	private List<Storie> stories;
	private List<Project> projects;

	public Developer() {
		this(null, "NO NAME");
	}

	public Developer(String id, String identity) {
		super(id);
		this.identity = identity;
		stories = new ArrayList<>();
		projects = new ArrayList<>();
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public List<Storie> getStories() {
		return stories;
	}

	public void setStories(List<Storie> stories) {
		this.stories = stories;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return identity + " (" + projects.size() + " projects)";
	}

}
