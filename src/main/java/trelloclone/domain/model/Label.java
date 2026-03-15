package trelloclone.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Label {
    private final String id;
    private String title;
    private String colorHex;

    public Label(String title, String colorHex) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.colorHex = colorHex;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return Objects.equals(id, label.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
