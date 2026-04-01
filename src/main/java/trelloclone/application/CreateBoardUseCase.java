package trelloclone.application;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.User;
import trelloclone.domain.port.BoardRepository;
import java.util.Optional;

public class CreateBoardUseCase {
    private final BoardRepository repository;

    public CreateBoardUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public Board execute(String title, String ownerEmail) {
        Optional<Board> existingBoard = repository.findByOwnerEmail(ownerEmail);
        if (existingBoard.isPresent()) {
            return existingBoard.get();
        }

        User owner = new User(ownerEmail);
        Board board = new Board(title, owner);
        repository.save(board);
        return board;
    }
}
