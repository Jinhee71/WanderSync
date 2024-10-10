import java.util.ArrayList;
import java.util.Date;
import java.util.List;


// Task as the generic function of the class
// - abstract to allow for further specialization
abstract class Task {
    private String title;
    private String description;
    private Date dueDate;
    private String status;
    private String priority;

    public Task(String title, String description, Date dueDate, String status, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public String getStatus() {
        return this.status;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void gsetDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setDueDate(Date newDueDate) {
        this.dueDate = newDueDate;
    }

    public void getStatus(String newStatus) {
        this.status = newStatus;
    }

    public void getPriority(String newPriority) {
        this.priority = newPriority;
    }
}



class Project {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<Task> tasks;
    private List<TeamMember> teamMembers;


    public Project(String name, String description, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void addTeamMember(TeamMember tm) {
        this.teamMembers.add(tm);
    }

    public void removeTeamMember(TeamMember tm) {
        this.teamMembers.remove(tm);
    }
}


class TeamMember {
    private String name;
    private String email;

    public TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void joinProject(Project project) {
        project.addTeamMember(this);
    }

    public void leaveProject(Project project) {
        project.removeTeamMember(this);
    }
}