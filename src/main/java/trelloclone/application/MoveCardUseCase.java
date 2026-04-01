package trelloclone.application;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.Card;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.model.User;
import trelloclone.domain.port.BoardRepository;

public class MoveCardUseCase {
    private final BoardRepository repository;

    public MoveCardUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public void execute(String boardId, String cardId, String fromListId, String toListId, String userEmail) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Tablero no encontrado"));
        User actor = new User(userEmail);

        TaskList fromList = board.getLists().stream()
                .filter(l -> l.getId().equals(fromListId))
                .findFirst()
                .orElse(null);

        TaskList toList = board.getLists().stream()
                .filter(l -> l.getId().equals(toListId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Lista destino no encontrada"));

        Card cardToMove = fromList != null ? fromList.getCards().stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada en la lista de origen")) : null;

        if (cardToMove == null) {
            throw new IllegalArgumentException("Restricciones de movimiento inválidas");
        }

        board.moveCard(cardToMove, fromList, toList, actor);
        repository.save(board);
    }
}
