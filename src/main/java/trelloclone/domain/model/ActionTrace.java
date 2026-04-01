package trelloclone.domain.model;

import java.time.LocalDateTime;

public class ActionTrace {
    private final User user;
    private final String action;
    private final LocalDateTime date;

    public ActionTrace(User user, String action) {
        this.user = user;
        this.action = action;
        this.date = LocalDateTime.now();
    }

    public User getUser() { return user; }
    public String getAction() { return action; }
    public LocalDateTime getDate() { return date; }

    @Override
    public String toString() {
        return date + " - " + user.getEmail() + ": " + action;
    }
}
