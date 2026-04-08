package trelloclone.application.template;

import java.util.List;

public class YamlBoardTemplate {
    private String title;
    private List<YamlListTemplate> lists;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<YamlListTemplate> getLists() { return lists; }
    public void setLists(List<YamlListTemplate> lists) { this.lists = lists; }

    public static class YamlListTemplate {
        private String name;
        private List<YamlCardTemplate> cards;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<YamlCardTemplate> getCards() { return cards; }
        public void setCards(List<YamlCardTemplate> cards) { this.cards = cards; }
    }

    public static class YamlCardTemplate {
        private String title;
        private String description;
        private String type; // TAREA o CHECKLIST

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}
