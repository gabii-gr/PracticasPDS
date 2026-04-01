package trelloclone.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "label")
public class LabelEntity {

    @Id
    private String id;

    private String title;
    private String colorHex;

    public LabelEntity() {}

    public LabelEntity(String id, String title, String colorHex) {
        this.id = id;
        this.title = title;
        this.colorHex = colorHex;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
}
