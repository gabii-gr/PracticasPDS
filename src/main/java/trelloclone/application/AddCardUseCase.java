package trelloclone.application;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.Card;
import trelloclone.domain.model.TaskCard;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.model.User;
import trelloclone.domain.port.BoardRepository;

public class AddCardUseCase {
    private final BoardRepository repository;

    public AddCardUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public Card execute(String boardId, String listId, String title, String description, String userEmail) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Tablero no encontrado"));
        User actor = new User(userEmail);

        TaskList targetList = board.getLists().stream()
                .filter(l -> l.getId().equals(listId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));

        Card newCard = new TaskCard(title, description);
        board.addCardToList(newCard, targetList, actor);

        repository.save(board);
        return newCard;
    }
}
