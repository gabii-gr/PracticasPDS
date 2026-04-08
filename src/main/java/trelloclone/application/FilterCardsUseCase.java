package trelloclone.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import trelloclone.domain.model.Board;
import trelloclone.domain.model.Card;
import trelloclone.domain.model.Label;
import trelloclone.domain.model.TaskList;
import trelloclone.domain.port.BoardRepository;

public class FilterCardsUseCase {
    private final BoardRepository repository;

    public FilterCardsUseCase(BoardRepository repository) {
        this.repository = repository;
    }

    public Map<TaskList, List<Card>> execute(String boardId, List<String> labelIds) {
        Board board = repository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Tablero no encontrado"));

        Map<TaskList, List<Card>> filteredCardsByList = new HashMap<>();

        for (TaskList list : board.getLists()) {
            List<Card> matchingCards = list.getCards().stream()
                    .filter(card -> matchesLabels(card, labelIds))
                    .collect(Collectors.toList());
                    
            if (!matchingCards.isEmpty()) {
                filteredCardsByList.put(list, matchingCards);
            }
        }

        return filteredCardsByList;
    }

    private boolean matchesLabels(Card card, List<String> requiredLabelIds) {
        if (requiredLabelIds == null || requiredLabelIds.isEmpty()) return true;
        
        List<String> cardLabelIds = card.getLabels().stream()
                .map(Label::getId)
                .collect(Collectors.toList());
                
        // Devuelve verdadero si la tarjeta tiene AL MENOS UNA de las etiquetas requeridas
        return requiredLabelIds.stream().anyMatch(cardLabelIds::contains);
    }
}
