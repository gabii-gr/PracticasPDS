package trelloclone.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public abstract class Card {
    protected final String id;
    protected String title;
    protected String description;
    protected List<Label> labels;
    protected Set<String> visitedListIds;

    public Card(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.labels = new ArrayList<>();
        this.visitedListIds = new HashSet<>();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public List<Label> getLabels() { return labels; }
    public void addLabel(Label label) {
        if (!labels.contains(label)) labels.add(label);
    }
    public void removeLabel(Label label) {
        labels.remove(label);
    }

    public Set<String> getVisitedListIds() { return visitedListIds; }
    public void addVisitedListId(String listId) { 
        this.visitedListIds.add(listId); 
    }
    public void setVisitedListIds(Set<String> visitedListIds) {
        this.visitedListIds = visitedListIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
