package trelloclone.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TaskList {
    private final String id;
    private String name;
    private List<Card> cards;
    private Integer maxItems; // Límite de elementos basado en regla
    private List<String> requiredPreviousListIds;

    public TaskList(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.cards = new ArrayList<>();
        this.requiredPreviousListIds = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<Card> getCards() { return cards; }
    
    public Integer getMaxItems() { return maxItems; }
    public void setMaxItems(Integer maxItems) { this.maxItems = maxItems; }
    
    public List<String> getRequiredPreviousListIds() { return requiredPreviousListIds; }
    public void setRequiredPreviousListIds(List<String> requiredPreviousListIds) { this.requiredPreviousListIds = requiredPreviousListIds; }
    public void addRequiredPreviousListId(String listId) { this.requiredPreviousListIds.add(listId); }

    public void addCard(Card card) {
        if (maxItems != null && cards.size() >= maxItems) {
            throw new IllegalStateException("Límite máximo de elementos alcanzado en la lista: " + name);
        }
        if (requiredPreviousListIds != null && !requiredPreviousListIds.isEmpty()) {
            for (String reqId : requiredPreviousListIds) {
                if (!card.getVisitedListIds().contains(reqId)) {
                    throw new IllegalStateException("La tarjeta debe pasar por la lista ID " + reqId + " antes de moverse a " + name);
                }
            }
        }
        if (!cards.contains(card)) {
            cards.add(card);
            card.addVisitedListId(this.id);
        }
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
