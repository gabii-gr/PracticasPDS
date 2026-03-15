package trelloclone.domain.model;

public class TaskCard extends Card {
    private User assignedUser;
    private boolean completed;

    public TaskCard(String title, String description) {
        super(title, description);
        this.completed = false;
    }

    public User getAssignedUser() { return assignedUser; }
    public void setAssignedUser(User assignedUser) { this.assignedUser = assignedUser; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
