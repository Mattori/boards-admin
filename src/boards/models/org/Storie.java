package boards.models.org;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Storie extends Model {
	@Expose
	private String code;
	@Expose
	private String descriptif;
	@Expose
	private Project project;
	@Expose
	private Developer developer;
	@Expose
	private Step step;
	@Expose
	private List<Tag> tags;
	@Expose
	private List<Task> tasks;

	public Storie() {
		this(null, "NO_CODE", "Aucun", null);
	}

	public Storie(String id, String code, String descriptif, Developer developer,Project project,List<Tag> tags,List<Task> tasks) {
		this(id, code, descriptif, null);
	}

	public Storie(String id, String code, String descriptif,Project project) {
		super(id);
		this.code = code;
		this.descriptif = descriptif;
		setProject(project);
		this.tags = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		if (this.developer != null) {
			this.developer.getStories().remove(this);
		}
		this.developer = developer;
		developer.getStories().add(this);
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescriptif() {
		return descriptif;
	}

	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
		if (project != null)
			project.getStories().add(this);
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		String result = code;
		if (developer != null)
			result += "->" + developer;
		return result;
	}

}
