package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TASK")
public class TaskCardEntity extends CardEntity {

    private String assignedUserEmail;
    private boolean completed;

    public TaskCardEntity() {}

    public TaskCardEntity(String id, String title, String description, String assignedUserEmail, boolean completed) {
        super(id, title, description);
        this.assignedUserEmail = assignedUserEmail;
        this.completed = completed;
    }

    public String getAssignedUserEmail() { return assignedUserEmail; }
    public void setAssignedUserEmail(String assignedUserEmail) { this.assignedUserEmail = assignedUserEmail; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
