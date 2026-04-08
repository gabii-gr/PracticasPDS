package trelloclone.application;

import java.util.ArrayList;
import java.util.List;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.Card;
import trelloclone.domain.model.ChecklistCard;
import trelloclone.domain.model.TaskCard;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.model.User;
import trelloclone.domain.port.BoardRepository;

public class CompactBoardUseCase {
    private final BoardRepository repository;

    public CompactBoardUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public void execute(String boardId, String userEmail) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Tablero no encontrado"));
        User actor = new User(userEmail);

        // Buscar o crear la lista de "Archivado"
        TaskList archiveList = board.getLists().stream()
                .filter(l -> l.getName().equalsIgnoreCase("Archivado") || l.getName().equalsIgnoreCase("Archived"))
                .findFirst()
                .orElse(null);

        if (archiveList == null) {
            archiveList = new TaskList("Archivado");
            board.addList(archiveList, actor);
        }

        List<Card> cardsToMove = new ArrayList<>();
        List<TaskList> sources = new ArrayList<>();

        for (TaskList list : board.getLists()) {
            if (list == archiveList) continue; // Saltar la lista de archivo en sí

            for (Card card : list.getCards()) {
                if (shouldArchive(card)) {
                    cardsToMove.add(card);
                    sources.add(list);
                }
            }
        }

        for (int i = 0; i < cardsToMove.size(); i++) {
            board.moveCard(cardsToMove.get(i), sources.get(i), archiveList, actor);
        }

        if (!cardsToMove.isEmpty()) {
            board.recordAction(actor, "Tablero compactado: Auto-archivadas " + cardsToMove.size() + " tarjetas completadas.");
            repository.save(board);
        }
    }

    private boolean shouldArchive(Card card) {
        if (card instanceof TaskCard) {
            return ((TaskCard) card).isCompleted();
        } else if (card instanceof ChecklistCard) {
            return ((ChecklistCard) card).isAllDone();
        }
        return false;
    }
}
