package boards.models.org;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Project extends Model {
	@Expose
	private String name;
	@Expose
	private String descriptif;
	@Expose
	private Date startDate;
	@Expose
	private Date dueDate;
	private List<Story> stories;
	@Expose
	private Developer owner;

	public Project() {
		this("sans nom", "aucun");
	}

	public Project(String name, String descriptif) {
		this(name, descriptif, Calendar.getInstance().getTime(), null);
	}

	public Project(String name, String descriptif, Date startDate, Date dueDate) {
		this(null, name, descriptif, startDate, dueDate);
	}

	public Project(String id, String name, String descriptif, Date startDate, Date dueDate) {
		super(id);
		this.name = name;
		this.descriptif = descriptif;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.stories = new ArrayList<>();
	}

	public void addStory() {
		stories.add(new Story());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptif() {
		return descriptif;
	}

	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	public Developer getOwner() {
		return owner;
	}

	public void setOwner(Developer owner) {
		this.owner = owner;
		if (owner != null)
			owner.getProjects().add(this);
	}

	@Override
	public String toString() {
		String result = name + " (" + descriptif + ")";
		result += " du " + SimpleDateFormat.getDateInstance().format(startDate);
		if (dueDate != null)
			result += " au " + SimpleDateFormat.getInstance().format(dueDate);
		result += ", " + stories.size() + " story(ies)";
		return result;
	}

}
