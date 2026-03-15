package trelloclone.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ChecklistCard extends Card {
    private List<ChecklistItem> items;

    public ChecklistCard(String title, String description) {
        super(title, description);
        this.items = new ArrayList<>();
    }

    public List<ChecklistItem> getItems() { return items; }
    
    public void addItem(String text) {
        items.add(new ChecklistItem(text));
    }
    
    public void removeItem(ChecklistItem item) {
        items.remove(item);
    }
    
    public boolean isAllDone() {
        if (items.isEmpty()) return false;
        return items.stream().allMatch(ChecklistItem::isDone);
    }
}
