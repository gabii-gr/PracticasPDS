package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_list")
public class TaskListEntity {

    @Id
    private String id;
    private String name;
    private Integer maxItems;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_list_requirements", joinColumns = @JoinColumn(name = "task_list_id"))
    @Column(name = "required_list_id")
    private List<String> requiredPreviousListIds = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cards = new ArrayList<>();

    public TaskListEntity() {}

    public TaskListEntity(String id, String name, Integer maxItems) {
        this.id = id;
        this.name = name;
        this.maxItems = maxItems;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getMaxItems() { return maxItems; }
    public void setMaxItems(Integer maxItems) { this.maxItems = maxItems; }

    public List<String> getRequiredPreviousListIds() { return requiredPreviousListIds; }
    public void setRequiredPreviousListIds(List<String> requiredPreviousListIds) { this.requiredPreviousListIds = requiredPreviousListIds; }

    public BoardEntity getBoard() { return board; }
    public void setBoard(BoardEntity board) { this.board = board; }

    public List<CardEntity> getCards() { return cards; }
    public void setCards(List<CardEntity> cards) { this.cards = cards; }

    public void addCard(CardEntity card) {
        cards.add(card);
        card.setTaskList(this);
    }
}
