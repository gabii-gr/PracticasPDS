package es.unizar.pds.trelloclone.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.Card;
import trelloclone.domain.model.TaskCard;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.model.User;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private User owner;
    private Board board;

    @BeforeEach
    void setUp() {
        owner = new User("test@example.com");
        board = new Board("Test Board", owner);
    }

    @Test
    void testBoardCreationInit() {
        assertEquals("Test Board", board.getTitle());
        assertEquals(owner, board.getOwner());
        assertNotNull(board.getUrlHash());
        assertFalse(board.isLocked());
        assertEquals(1, board.getHistory().size()); // "Board created" action
    }

    @Test
    void testAddList() {
        TaskList list = new TaskList("TODO");
        board.addList(list, owner);
        assertTrue(board.getLists().contains(list));
        assertEquals(2, board.getHistory().size());
    }

    @Test
    void testAddCardToListBlockedWhenBoardLocked() {
        TaskList list = new TaskList("TODO");
        board.addList(list, owner);
        board.setLocked(true);
        
        Card card = new TaskCard("Task 1", "Desc");
        
        assertThrows(IllegalStateException.class, () -> {
            board.addCardToList(card, list, owner);
        });
    }

    @Test
    void testMoveCardAllowedWhenBoardLocked() {
        TaskList list1 = new TaskList("TODO");
        TaskList list2 = new TaskList("DOING");
        board.addList(list1, owner);
        board.addList(list2, owner);
        
        Card card = new TaskCard("Task 1", "Desc");
        board.addCardToList(card, list1, owner);
        
        board.setLocked(true);
        
        assertDoesNotThrow(() -> {
            board.moveCard(card, list1, list2, owner);
        });
        
        assertFalse(list1.getCards().contains(card));
        assertTrue(list2.getCards().contains(card));
    }
    
    @Test
    void testMaxItemsRuleInList() {
        TaskList list = new TaskList("TODO");
        list.setMaxItems(1);
        
        Card card1 = new TaskCard("Task 1", "Desc");
        Card card2 = new TaskCard("Task 2", "Desc");
        
        list.addCard(card1);
        assertThrows(IllegalStateException.class, () -> {
            list.addCard(card2);
        });
    }
}
