package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "checklist_item")
public class ChecklistItemEntity {

    @Id
    private String id;
    private String text;
    private boolean done;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private ChecklistCardEntity card;

    public ChecklistItemEntity() {}

    public ChecklistItemEntity(String id, String text, boolean done, ChecklistCardEntity card) {
        this.id = id;
        this.text = text;
        this.done = done;
        this.card = card;
    }

    public String getId() { return id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
    public ChecklistCardEntity getCard() { return card; }
    public void setCard(ChecklistCardEntity card) { this.card = card; }
}
