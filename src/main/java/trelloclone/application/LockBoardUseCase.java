package trelloclone.application;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.User;
import trelloclone.domain.port.BoardRepository;

public class LockBoardUseCase {
    private final BoardRepository repository;

    public LockBoardUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public void execute(String boardId, boolean lockStatus, String userEmail) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Tablero no encontrado"));
        User actor = new User(userEmail);

        if (!board.getOwner().equals(actor)) {
            throw new SecurityException("Solo el propietario puede bloquear/desbloquear el tablero");
        }

        board.setLocked(lockStatus);
        board.recordAction(actor, (lockStatus ? "Locked" : "Unlocked") + " the board");
        
        repository.save(board);
    }
}
