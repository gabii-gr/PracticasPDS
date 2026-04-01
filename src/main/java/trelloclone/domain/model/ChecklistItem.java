package trelloclone.domain.model;

import java.util.Objects;
import java.util.UUID;

public class ChecklistItem {
    private final String id;
    private String text;
    private boolean done;

    public ChecklistItem(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.done = false;
    }

    public String getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChecklistItem that = (ChecklistItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
