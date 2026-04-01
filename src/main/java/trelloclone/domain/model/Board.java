package trelloclone.domain.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Board {
    private final String id;
    private String title;
    private final User owner;
    private final String urlHash;
    private List<TaskList> lists;
    private List<ActionTrace> history;
    private boolean locked;

    public Board(String title, User owner) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.owner = owner;
        this.urlHash = generateUrlHash(this.id);
        this.lists = new ArrayList<>();
        this.history = new ArrayList<>();
        this.locked = false;
        
        recordAction(owner, "Tablero creado");
    }

    private String generateUrlHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString().substring(0, 16);
        }
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public User getOwner() { return owner; }
    public String getUrlHash() { return urlHash; }
    
    public List<TaskList> getLists() { return lists; }
    public List<ActionTrace> getHistory() { return history; }
    
    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    public void addList(TaskList list, User actor) {
        checkBoardIsUnlocked();
        if (!lists.contains(list)) {
            lists.add(list);
            recordAction(actor, "Lista añadida: " + list.getName());
        }
    }

    public void removeList(TaskList list, User actor) {
        checkBoardIsUnlocked();
        if (lists.remove(list)) {
            recordAction(actor, "Lista eliminada: " + list.getName());
        }
    }

    public void moveCard(Card card, TaskList from, TaskList to, User actor) {
        
        if (from != null && lists.contains(from)) {
            from.removeCard(card);
        }
        if (to != null && lists.contains(to)) {
            to.addCard(card); // lanza excepción si se supera el límite de la lista
            recordAction(actor, "Tarjeta movida: " + card.getTitle() + " a la lista: " + to.getName());
        }
    }

    public void addCardToList(Card card, TaskList list, User actor) {
        checkBoardIsUnlocked(); 
        if (lists.contains(list)) {
            list.addCard(card);
            recordAction(actor, "Tarjeta añadida: " + card.getTitle() + " a la lista: " + list.getName());
        }
    }

    private void checkBoardIsUnlocked() {
        if (locked) {
            throw new IllegalStateException("El tablero está bloqueado temporalmente. Solo se permite mover tarjetas.");
        }
    }

    public void recordAction(User user, String actionDescription) {
        history.add(new ActionTrace(user, actionDescription));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
