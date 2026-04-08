package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
public class BoardEntity {

    @Id
    private String id;
    
    private String title;
    private String ownerEmail;
    private String urlHash;
    private boolean locked;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskListEntity> lists = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionTraceEntity> history = new ArrayList<>();

    public BoardEntity() {}

    public BoardEntity(String id, String title, String ownerEmail, String urlHash, boolean locked) {
        this.id = id;
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.urlHash = urlHash;
        this.locked = locked;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }
    public String getUrlHash() { return urlHash; }
    public void setUrlHash(String urlHash) { this.urlHash = urlHash; }
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    public List<TaskListEntity> getLists() { return lists; }
    public void setLists(List<TaskListEntity> lists) { this.lists = lists; }

    public List<ActionTraceEntity> getHistory() { return history; }
    public void setHistory(List<ActionTraceEntity> history) { this.history = history; }

    public void addList(TaskListEntity list) {
        lists.add(list);
        list.setBoard(this);
    }

    public void addHistoryTrace(ActionTraceEntity trace) {
        history.add(trace);
    }
}
