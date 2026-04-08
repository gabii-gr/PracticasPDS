package trelloclone.application;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.model.User;
import trelloclone.domain.port.BoardRepository;

public class AddListUseCase {
    private final BoardRepository repository;

    public AddListUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public void execute(String boardId, String listName, String userEmail) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Tablero no encontrado"));
        User actor = new User(userEmail);

        TaskList newList = new TaskList(listName);
        board.addList(newList, actor);
        
        repository.save(board);
    }
}
