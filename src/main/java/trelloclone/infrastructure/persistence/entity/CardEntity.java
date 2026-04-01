package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "card")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type", discriminatorType = DiscriminatorType.STRING)
public abstract class CardEntity {

    @Id
    protected String id;

    protected String titulo;
    protected String descripcion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "card_labels",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    protected List<LabelEntity> labels = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "card_visited_lists", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "list_id")
    protected Set<String> visitedListIds = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    protected TaskListEntity taskList;

    public CardEntity() {}

    public CardEntity(String id, String title, String description) {
        this.id = id;
        this.titulo = title;
        this.descripcion = description;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String title) { this.titulo = title; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String description) { this.descripcion = description; }
    
    public List<LabelEntity> getLabels() { return labels; }
    public void setLabels(List<LabelEntity> labels) { this.labels = labels; }
    
    public Set<String> getVisitedListIds() { return visitedListIds; }
    public void setVisitedListIds(Set<String> visitedListIds) { this.visitedListIds = visitedListIds; }
    
    public TaskListEntity getTaskList() { return taskList; }
    public void setTaskList(TaskListEntity taskList) { this.taskList = taskList; }
}
