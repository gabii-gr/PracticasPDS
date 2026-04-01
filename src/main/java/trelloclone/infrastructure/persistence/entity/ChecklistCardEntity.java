package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CHECKLIST")
public class ChecklistCardEntity extends CardEntity {

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistItemEntity> items = new ArrayList<>();

    public ChecklistCardEntity() {}

    public ChecklistCardEntity(String id, String title, String description) {
        super(id, title, description);
    }

    public List<ChecklistItemEntity> getItems() { return items; }
    public void setItems(List<ChecklistItemEntity> items) { this.items = items; }
    
    public void addItem(ChecklistItemEntity item) {
        items.add(item);
        item.setCard(this);
    }
}
